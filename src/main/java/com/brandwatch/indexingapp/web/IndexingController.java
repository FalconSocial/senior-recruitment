package com.brandwatch.indexingapp.web;

import com.brandwatch.indexingapp.data.entity.Content;
import com.brandwatch.indexingapp.data.entity.Query;
import com.brandwatch.indexingapp.service.IndexingService;
import com.brandwatch.indexingapp.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class IndexingController {

    private final List<IndexingService> indexingServices;

    private final QueryService queryService;

    @GetMapping("/index/{network}/index")
    // Index data for the given network, and match queries for found new contents
    public ResponseEntity indexData(@PathVariable String network) {
        new Thread(() -> this.indexingServices.stream().filter(service -> service.getNetwork().equalsIgnoreCase(network)).forEach(IndexingService::indexAndSendNotifications)).run();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/index/{network}")
    // Get all stored data
    public ResponseEntity<List<Content>> getContents(@PathVariable String network) {
        return this.indexingServices.stream().filter(service -> service.getNetwork().equalsIgnoreCase(network))
                .findAny()
                .map(service -> service.getContents())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/query/create")
    // Create a new query for the user
    public ResponseEntity<UUID> createQuery(@RequestParam String network, @RequestParam String matchText, @RequestParam String user) {
        return ResponseEntity.ok(this.queryService.create(network, matchText, user));
    }
    @PostMapping("/query/delete")
    // Delete queries based on ID or user
    public ResponseEntity<Query> calculateAverages(@RequestParam(required = false) String user, @RequestParam(required = false) UUID id) {
        if (user == null) {
            this.queryService.delete(id);
        } else {
            this.queryService.deleteAll(user);
        }
        return ResponseEntity.ok().build();
    }

}
