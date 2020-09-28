package br.com.miqueiascoutinho.aws.apis.tos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsBucketFile {
	
	@JsonProperty(value = "file-name")
	private String fileName;
}
