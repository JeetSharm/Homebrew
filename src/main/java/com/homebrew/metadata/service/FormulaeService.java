package com.homebrew.metadata.service;

import com.homebrew.metadata.model.response.MetadataResponse;
import com.homebrew.metadata.repository.FormulaeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FormulaeService {

    private FormulaeRepository formulaeRepository;

    @Autowired
    public FormulaeService(FormulaeRepository formulaeRepository) {
        this.formulaeRepository = formulaeRepository;
    }

    public ResponseEntity<MetadataResponse> getFormulaeMetadata(String name) {
        // call Formula repository
        log.debug("calling Formula repository");
        ResponseEntity<MetadataResponse> response = formulaeRepository.getFormulaeMetadata(name);
        log.info("Call to Homebrew API completed with status code [" + response.getStatusCode().value() + "]");

        // Prepare response headers
        log.debug("Crafting response headers for Metadata API");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.debug("Response headers crafted successfully for Metadata API");
        log.info("Returning response from Metadata API with status code [" + response.getStatusCode().value() + "]");

        return new ResponseEntity<MetadataResponse>(response.getBody(), headers, response.getStatusCode());
    }

}
