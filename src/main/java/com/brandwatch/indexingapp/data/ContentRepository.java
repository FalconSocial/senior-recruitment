package com.brandwatch.indexingapp.data;

import com.brandwatch.indexingapp.data.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {

    List<Content> findByNetwork(String network);
}
