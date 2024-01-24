package com.brandwatch.indexingapp.service;

import com.brandwatch.indexingapp.data.QueryRepository;
import com.brandwatch.indexingapp.data.entity.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;

    public UUID create(String network, String matchText, String user) {
        Query query = new Query(null, matchText, network, user);

        query = this.queryRepository.save(query);

        return query.getId();
    }

    public void deleteAll(String user) {
        List<Query> byUser = this.queryRepository.findByUserId(user);
        this.queryRepository.deleteAll(byUser);
    }

    public void delete(UUID id) {
        this.queryRepository.deleteById(id);
    }
}
