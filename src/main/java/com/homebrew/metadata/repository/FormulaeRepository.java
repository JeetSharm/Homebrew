package com.homebrew.metadata.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.homebrew.metadata.model.response.MetadataResponse;
@Slf4j
@Repository
public class FormulaeRepository {

	private RestTemplate restTemplate;

	@Value("${uri.scheme}")
	private String scheme;
	@Value("${uri.host}")
	private String host;
	@Value("${uri.path}")
	private String path;

	@Autowired
	public FormulaeRepository(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<MetadataResponse> getFormulaeMetadata(String name) {
		// Prepare request headers
		log.debug("Crafting request headers for Homebrew API");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		log.debug("Request headers crafted successfully for Homebrew API");

		// Construct URI
		log.debug("Building URI for Homebrew API");
		StringBuilder pathWithResource = new StringBuilder(path);
		pathWithResource.append(name).append(".json");
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance().scheme(scheme).host(host)
				.path(pathWithResource.toString()).encode();
		log.debug("URI built successfully for Homebrew API");

		// Send the GET request
		log.info("Calling Homebrew API");
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, MetadataResponse.class);

	}
}
