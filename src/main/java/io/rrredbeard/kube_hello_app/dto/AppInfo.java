package io.rrredbeard.kube_hello_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
public class AppInfo {

	@JsonProperty(value = "app_id")
	private String appId;

	@JsonProperty()
	private String version;

	@JsonProperty(value = "extra_param")
	private String extraParam;

	@JsonProperty(value = "__env")
	private JsonNode environment;

}
