package com.invoo.orchestrator.client.catalog;

import org.springframework.web.reactive.function.client.WebClient;


public class CatalogClient {

    private final WebClient client;

    public CatalogClient(WebClient client) {
        this.client = client;
    }

    public WebClient getClient() {
        return client;
    }
}
