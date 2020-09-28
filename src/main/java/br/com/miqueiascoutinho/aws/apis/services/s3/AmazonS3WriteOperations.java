package br.com.miqueiascoutinho.aws.apis.services.s3;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Region;

import br.com.miqueiascoutinho.aws.apis.exceptions.AmazonS3CustomException;
import br.com.miqueiascoutinho.aws.apis.exceptions.AmazonS3CustomNotFoundException;
import br.com.miqueiascoutinho.aws.apis.tos.AwsBucket;

@Service
public class AmazonS3WriteOperations {

	private final String ERROR_BUCKET_NAME_NOT_AVAILABLE = "Já existe um bucket com esse nome, por favor altere o nome e tente novamente";
	
	@Autowired
	private AmazonS3 amazonS3ClientNormal;

	public Bucket createBucket(AwsBucket bucket) {
		CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket.getName(), Region.SA_SaoPaulo);
		try {
			return amazonS3ClientNormal.createBucket(createBucketRequest);

		} catch (Exception e) {
			if ((e.getLocalizedMessage().contains("bucket name is not available") ||
					e.getLocalizedMessage().contains("BucketAlreadyOwnedByYou"))) {
				
				throw new AmazonS3CustomException(ERROR_BUCKET_NAME_NOT_AVAILABLE);
				
			} else {
				throw new AmazonS3CustomException(e.getMessage());
			}
			
		}
	}

	public void deleteBucket(String bucketName) {
		try {
			amazonS3ClientNormal.deleteBucket(bucketName);
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
			amazonS3ClientNormal.putObject(bucketName, fileName, file);
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
