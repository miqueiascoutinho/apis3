package br.com.miqueiascoutinho.apis3.tos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsBucketFile {
	
	@JsonProperty(value = "file-name")
	private String fileName;
}
