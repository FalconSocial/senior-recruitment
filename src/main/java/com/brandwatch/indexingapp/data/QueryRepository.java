package com.brandwatch.indexingapp.data;

import com.brandwatch.indexingapp.data.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QueryRepository extends JpaRepository<Query, UUID> {

    List<Query> findByUserId(String userId);

    List<Query> findByNetwork(String network);
}
