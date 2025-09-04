package com.invoo.orchestrator.client.catalog.service;

import com.invoo.global.catalog.CategoryDto;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.application.exceptions.ClientConnectionException;
import com.invoo.orchestrator.client.catalog.CatalogClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;


@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger( CategoryService.class);
    private final CatalogClient catalogClient;
    private final WebClient client;

    public CategoryService(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
        client = catalogClient.getClient();
    }


    public List<CategoryDto> getCategories(String idCatalog) {
        return this.client.get()
                .uri("/categories/catalog/{idCompany}", idCatalog)
                .retrieve()
                .bodyToFlux( CategoryDto.class)
                .retryWhen(retry())
                .collectList()
                .doOnSuccess( catalogs -> log.info("Catalogs retrieved successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }








    private Retry retry() {
        return Retry.fixedDelay(5, Duration.ofSeconds(1))
                .doBeforeRetry(rs -> {
                    log.error( "Catalog service call failed. retrying: {}", rs.failure().getMessage() );
                    if (rs.totalRetries() == 4) {
                        throw new ClientConnectionException( "Catalog" );
                    }
                });
    }


}
