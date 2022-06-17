package com.homebrew.metadata.controller;

import com.homebrew.metadata.model.response.MetadataResponse;
import com.homebrew.metadata.service.FormulaeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FormulaeController {

    private FormulaeService formulaeService;

    @Autowired
    public FormulaeController(FormulaeService formulaeService) {
        this.formulaeService = formulaeService;
    }

    /**
     * Metadata API
     * GET /formula
     *
     * @param name Name of the formula
     * @return MetadataResponse (status code 2XX series)
     * of Error response (status code 4XX or 5XX series).
     */
    @GetMapping("/formula")
    public ResponseEntity<MetadataResponse> getFormulaMetadata(@RequestParam String name) {
        log.info("Request received to Metadata API for formula : " + name);
        log.debug("calling Formula service ");
        return formulaeService.getFormulaeMetadata(name);
    }

}
