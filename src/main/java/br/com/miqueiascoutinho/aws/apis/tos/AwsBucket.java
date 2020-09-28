package br.com.miqueiascoutinho.aws.apis.tos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsBucket {
	
	@JsonProperty(value = "bucket-name")
	private String name;
	
	public AwsBucket() {
	}
	
}
