package io.rrredbeard.kube_hello_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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

}
