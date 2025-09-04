package com.invoo.orchestrator.client.catalog.service;


import com.invoo.global.catalog.product.EditedProductRequest;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.application.exceptions.ClientConnectionException;
import com.invoo.orchestrator.client.catalog.CatalogClient;
import com.invoo.orchestrator.client.catalog.dto.products.ProductResponse;
import com.invoo.orchestrator.client.catalog.dto.products.ProductWithCategoryDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;


@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final CatalogClient catalogClient;
    private final WebClient client;

    public ProductService(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
        client = catalogClient.getClient();
    }


    public ResponseEntity<?> createProduct(ProductWithCategoryDto productWithCategoryDto) {
        var response = this.client.post()
                .uri("/products/save")
                .bodyValue(productWithCategoryDto)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToFlux( ProductResponse.class )
                .collectList()
                .doOnSuccess( catalog -> log.info("Product created successfully!") )
                .onErrorResume( WebClientRequestException.class,
                        e -> ApplicationExceptions.connectionException( "Product" ) )
                .block();
        return ResponseEntity.ok().body( response );
    }

    public ProductResponse getProductById(@Valid Long productId) {
        return this.client.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .retryWhen(retry())
                .doOnSuccess( catalogs -> log.info("Product retrieved successfully! by id={}", productId) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }

    public List<ProductResponse> getProductsByCatalogId(Long catalogId) {
        return this.client.get()
                .uri("/products/catalog/{catalogId}", catalogId)
                .retrieve()
                .bodyToFlux(ProductResponse.class)
                .retryWhen(retry())
                .collectList()
                .doOnSuccess( catalog -> log.info("Product with [catalogId: {}] retrieved successfully!", catalogId) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .onErrorResume( RetryException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }
     public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        return this.client.get()
                .uri("/products/category/{categoryId}", categoryId)
                .retrieve()
                .bodyToFlux(ProductResponse.class)
                .retryWhen(retry())
                .collectList()
                .doOnSuccess( catalog -> log.info("Product with [categoryId: {}] retrieved successfully!", categoryId) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .onErrorResume( RetryException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }


    public String deleteProductById(@Valid Long id) {

        return this.client.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono(String.class)
                .doOnSuccess( catalogs -> log.info("Product with [id: {}] deleted successfully!", id) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }


    public EditedProductRequest updateProductById(@Valid Long id, EditedProductRequest productRequest) {
        log.info("Updating product with ID: {}", id);
        log.info("Product update request: {}", productRequest);
        return this.client.put()
                .uri("/products/{id}", id)
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body( BodyInserters.fromValue(productRequest))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono( EditedProductRequest.class )
                .doOnSuccess( catalogs -> log.info("Product with [id: {}] updated successfully!", id) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

    }










    private Retry retry() {
        return Retry.fixedDelay(5, Duration.ofSeconds(1))
                .doBeforeRetry(rs -> {
                    log.error( "Product service call failed. retrying: {}", rs.failure().getMessage() );
                    if (rs.totalRetries() == 4) {
                        throw new ClientConnectionException( "Catalog Client" );
                    }
                });
    }

}
