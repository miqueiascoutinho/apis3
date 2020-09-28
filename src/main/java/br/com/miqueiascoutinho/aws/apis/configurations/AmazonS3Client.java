package br.com.miqueiascoutinho.aws.apis.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Client {

	private static final String AWS_REGION = "REPLACE_THIS_VALUE";
	private static final String AWS_ACCESS_KEY_ID = "REPLACE_THIS_VALUE";
	private static final String AWS_SECRET_ACCESS_KEY = "REPLACE_THIS_VALUE";

	/*
	 * Para executar nesse formato, é necessário que sua credencial da AWS esteja
	 * configurada.
	 * 
	 * Para mais informações, ler o help de aws configure
	 */
	
	@Bean
	public AmazonS3 amazonS3ClientNormal() {
		return AmazonS3ClientBuilder
				.standard()
				.build();
	}

	@Bean
	public AmazonS3 amazonS3ClientWithRegion() {
		return AmazonS3ClientBuilder
				.standard()
				.withRegion(AWS_REGION)
				.build();
	}

	@Bean
	public AmazonS3 amazonS3ClientWithCredentials() {
		BasicAWSCredentials awsCreds = 
				new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);

		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();
	}
}
