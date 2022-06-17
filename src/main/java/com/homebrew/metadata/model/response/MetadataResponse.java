package com.homebrew.metadata.model.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "desc", "versions", "dependencies", "generated_date" })
public class MetadataResponse {

	@JsonProperty("desc")
	private String description;

	@JsonProperty("versions")
	private Versions versions;

	@JsonProperty("dependencies")
	private String[] dependencies;

	@JsonProperty("generated_date")
	private String generated_date;

}
