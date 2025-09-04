package com.invoo.orchestrator.exposition.config.webclient;

import com.invoo.orchestrator.client.catalog.CatalogClient;
import com.invoo.orchestrator.client.invoice.service.InvoiceServiceClient;
import com.invoo.orchestrator.client.payment.service.PaymentServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceClientConfig {

    private static final Logger log = LoggerFactory.getLogger( ServiceClientConfig.class);

    @Bean
    public CatalogClient catalogClient(@Value("${api.catalog.service.url}") String u) {
        return new CatalogClient(createWebClient(u));
    }

    @Bean
    public InvoiceServiceClient invoiceServiceClient(@Value("${api.invoice.service.url}") String u) {
        return new InvoiceServiceClient(createWebClient(u));
    }

    @Bean
    public PaymentServiceClient paymentServiceClient(@Value("${api.payment.service.url}") String u) {
        return new PaymentServiceClient(createWebClient(u));
    }

    private WebClient createWebClient(String url) {
        log.info( "Creating web client for base url: {}", url);
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }

}
