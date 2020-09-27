package br.com.miqueiascoutinho.apis3.tos;

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
