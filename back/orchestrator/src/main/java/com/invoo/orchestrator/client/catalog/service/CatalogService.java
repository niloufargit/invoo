package com.invoo.orchestrator.client.catalog.service;

import com.invoo.global.catalog.CatalogRequest;
import com.invoo.global.catalog.CatalogsWithCategoriesDTO;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.application.exceptions.ClientConnectionException;
import com.invoo.orchestrator.client.catalog.CatalogClient;
import com.invoo.orchestrator.client.catalog.dto.CatalogResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.retry.RetryException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


@Service
public class CatalogService {

    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);
    private final CatalogClient catalogClient;
    private final WebClient client;

    public CatalogService(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
        client = catalogClient.getClient();
    }


    public ResponseEntity<?> createCatalog(CatalogRequest catalogRequest) {
        var response = this.client.post()
                .uri("/catalogs")
                .bodyValue(catalogRequest)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono( CatalogResponse.class )
                .doOnSuccess( catalog -> log.info("Catalog created successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
        return ResponseEntity.ok().body( response );
    }

    public List<CatalogResponse> getCatalogs(String id) {
        return this.client.get()
                .uri("/catalogs/entity/{id}", id)
                .retrieve()
                .bodyToFlux(CatalogResponse.class)
                .retryWhen(retry())
                .collectList()
                .doOnSuccess( catalogs -> log.info("Catalogs retrieved successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }

    public CatalogResponse getCatalogById(Long id) {
        return this.client.get()
                .uri("/catalogs/{id}", id)
                .retrieve()
                .bodyToMono(CatalogResponse.class)
                .retryWhen(retry())
                .doOnSuccess( catalog -> log.info("Catalog with [id: {}] retrieved successfully!", id) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .onErrorResume( RetryException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }

    public ResponseEntity<?> processExcelFile(MultipartFile file, CatalogRequest catalogRequest) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        try {
            builder.part("file", file.getBytes())
                    .header("Content-Disposition", "form-data; name=file; filename="+file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        builder.part("catalogRequest", catalogRequest, MediaType.APPLICATION_JSON);

        var response = this.client.post()
                .uri("/upload")

                .body( BodyInserters.fromMultipartData( builder.build() ) )
                .retrieve()
                .bodyToMono( String.class)
                .doOnSuccess( catalog -> log.info("Catalog uploaded successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .onErrorResume( RetryException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

        return ResponseEntity.ok().body( response );
    }

    public List<CatalogsWithCategoriesDTO> getCompanyCatalogs(Long companyId) {
        return this.client.get()
                .uri("/catalogs/company/{id}", companyId)
                .retrieve()
                .bodyToFlux( CatalogsWithCategoriesDTO.class)
                .retryWhen(retry())
                .collectList()
                .doOnSuccess( catalogs -> log.info("Catalogs retrieved successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();
    }

    public ResponseEntity<?> updateCatalog(@Valid CatalogRequest catalogRequest, Long idCatalog) {
        log.info("Updating Catalog with ID: {}", idCatalog);
        log.info("Catalog update request: {}", catalogRequest);
        var response = this.client.put()
                .uri("/catalogs/{id}", idCatalog)
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body( BodyInserters.fromValue(catalogRequest))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono( CatalogResponse.class )
                .doOnSuccess( catalogs -> log.info("Catalog with [id: {}] updated successfully!", idCatalog) )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

        return ResponseEntity.ok( response );
    }

    public ResponseEntity<?> deleteCatalogById(Long id) {
        log.info( "delete catalog by id : {}", id );

        var response = this.client.delete()
                .uri("/catalogs/{id}", id)
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono( String.class )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

        return ResponseEntity.ok( response );

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
