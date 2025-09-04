package com.invoo.orchestrator.domaine.poubelle.Entity.facture;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Float.parseFloat;

@Service
@RequiredArgsConstructor
public class FactureService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final FactureRepository factureRepository;

    public Facture createFacture(FactureRequest factureRequest, MultipartFile file) throws IOException {
        // Save the file locally
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
        Files.write(path, bytes);
        Facture facture = Facture.builder()
                .fileName(file.getOriginalFilename())
                .filePath(path.toString())
                .amount(parseFloat(factureRequest.getAmount()))
                .uploadTime(LocalDateTime.now())
                .build();

        return factureRepository.save(facture);
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture updateFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }
    public Resource loadFileAsResource(Long id) {
        try {
            Facture facture = factureRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Facture not found"));
            Path filePath = Paths.get(facture.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
