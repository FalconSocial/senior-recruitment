package com.brandwatch.indexingapp.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "query")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String queryText;

    private String network;

    private String userId;
}
