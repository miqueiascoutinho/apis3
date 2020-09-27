package br.com.miqueiascoutinho.apis3.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Region;

import br.com.miqueiascoutinho.apis3.exceptions.AmazonS3CustomException;
import br.com.miqueiascoutinho.apis3.exceptions.AmazonS3CustomNotFoundException;
import br.com.miqueiascoutinho.apis3.tos.AwsBucket;

@Service
public class AwsWriteOperations {

	@Autowired
	private AmazonS3 amazonS3Client;

	public Bucket createBucket(AwsBucket bucket) {
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket.getName(), Region.SA_SaoPaulo);
		try {
			return amazonS3Client.createBucket(createBucketRequest);

		} catch (Exception e) {
			throw new AmazonS3CustomException("Erro ao criar o bucket " + bucket.getName());
		}
	}

	public void deleteBucket(String bucketName) {
		try {
			amazonS3Client.deleteBucket(bucketName);
		} catch (AmazonS3Exception ae) {

			if (ae.getMessage().contains("NoSuchBucket")) {
				throw new AmazonS3CustomNotFoundException("O bucket " + bucketName + " não existe");
			} else {
				throw new AmazonS3CustomException(ae.getMessage());
			}
		} catch (Exception e) {
			throw new AmazonS3CustomException(e.getMessage());
		}

	}

	public void putObject(String bucketName, String fileName, File file) {

		try {
			amazonS3Client.putObject(bucketName, fileName, file);
		} catch (AmazonS3Exception ae) {

			if (ae.getMessage().contains("NoSuchBucket")) {
				throw new AmazonS3CustomNotFoundException("O bucket " + bucketName + " não existe");
			} else {
				throw new AmazonS3CustomException(ae.getMessage());
			}
		} catch (SdkClientException e) {
			throw new AmazonS3CustomNotFoundException("O arquivo informado não existe. Usar o seguinte exemplo como padrão: /home/seu-usuario/seu-file-upload.txt");
		}
	}
}
