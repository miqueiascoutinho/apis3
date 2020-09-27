package br.com.miqueiascoutinho.apis3.tos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsS3File {
	
	@JsonProperty(value = "aws-file-name")
	private String fileName;
	
	@JsonProperty("local-file-absolute-path")
	private String file;
	
	public AwsS3File() {
	}
 }
