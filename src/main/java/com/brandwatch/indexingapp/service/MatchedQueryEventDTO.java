package com.brandwatch.indexingapp.service;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchedQueryEventDTO {

    private final UUID queryId;

    private final String network;

    private final String content;
}
