package br.com.miqueiascoutinho.apis3.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.Bucket;

import br.com.miqueiascoutinho.apis3.controllers.errors.ApiError;
import br.com.miqueiascoutinho.apis3.services.AwsReadOperations;
import br.com.miqueiascoutinho.apis3.services.AwsWriteOperations;
import br.com.miqueiascoutinho.apis3.tos.AwsBucketFile;
import br.com.miqueiascoutinho.apis3.tos.AwsS3File;
import br.com.miqueiascoutinho.apis3.tos.AwsBucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(path = "/v1/aws/s3")
@Api(tags = "Operações API S3", value = "API com exemplos de integração com Amazon S3")
public class AwsController {

	@Autowired
	private AwsReadOperations awsOperations;
	
	@Autowired
	private AwsWriteOperations awsWriteOperations;
	
	@GetMapping(path = "/list-buckets")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Operação responsável em listar os buckets no Amazon S3")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AwsBucket.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ApiError.class), })
	public List<AwsBucket> listAwsS3Buckets() {
		return awsOperations.listBuckets();
	}

	@GetMapping(path = "/bucket-content")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Operação responsável em listar o conteúdo de um determinado bucket no Amazon S3")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = AwsBucketFile.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ApiError.class), })
	public List<AwsBucketFile> listBucketContent( @RequestParam(value = "bucket-name") String bucketName) {
			
		return awsOperations.listBucketContent(bucketName);
	}
	
	@PostMapping(path = "/bucket")
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Operação responsável em criar um bucket no Amazon S3")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Sucesso"),
			@ApiResponse(code = 400, message = "Bad Request", response = ApiError.class), })
	public Bucket createBucket(@RequestBody AwsBucket bucket) {
		return awsWriteOperations.createBucket(bucket);
	}
	
	@DeleteMapping(path = "/bucket/{bucket-name}")
	@ApiOperation(value = "Operação responsável em deletar um bucket no Amazon S3")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request", response = ApiError.class), 
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class)})
	public void deleteBucket(@PathVariable(value = "bucket-name") String bucketName) {
		awsWriteOperations.deleteBucket(bucketName);
	}
	
	@PutMapping(path = "/bucket/{bucket-name}")
	@ApiOperation(value = "Operação responsável em realizar o put (upload) de um arquivo local para um bucket específico no Amazon S3")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad Request", response = ApiError.class), 
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class)})
	public void putObject (@RequestBody AwsS3File file,
			@PathVariable(value = "bucket-name") String bucketName)  {
		
		File f = new File(file.getFile());
		awsWriteOperations.putObject(bucketName,
				file.getFileName(), 
				f);
	}
	
}
