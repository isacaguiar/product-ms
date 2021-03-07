package br.com.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"status_code", "error" })
public class CustomErrorResponse {
	
	@JsonProperty("status_code")
	private int status;
    private String error;

}
