package br.com.miqueiascoutinho.aws.apis.tos;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsS3File {
	
	@JsonProperty(value = "aws-file-name")
	@NotBlank(message = "aws-file-name é obrigatório")
	@ApiModelProperty(name = "aws-file-name", required = true)
	private String fileName;
	
	@JsonProperty("local-file-absolute-path")
	@NotBlank(message = "local-file-absolute-path é obrigatório")
	@ApiModelProperty(name = "local-file-absolute-path", required = true)
	private String file;
	
	public AwsS3File() {
	}
 }
