package br.com.miqueiascoutinho.apis3.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import br.com.miqueiascoutinho.apis3.exceptions.AmazonS3CustomException;
import br.com.miqueiascoutinho.apis3.tos.AwsBucketFile;
import br.com.miqueiascoutinho.apis3.tos.AwsBucket;

@Service
public class AwsReadOperations {

	@Autowired
	private AmazonS3 amazonS3Client;

	public List<AwsBucket> listBuckets() {
		List<Bucket> listaBuckets = amazonS3Client.listBuckets();
		List<AwsBucket> buckets = new ArrayList<>();

		listaBuckets.stream().forEach(b -> buckets.add(new AwsBucket(b.getName())));

		return buckets;
	}

	public List<AwsBucketFile> listBucketContent(final String bucketName) {

		try {
			List<S3ObjectSummary> lista = amazonS3Client.listObjects(bucketName).getObjectSummaries();

			List<AwsBucketFile> files = new ArrayList<>();

			lista.stream().forEach(b -> files.add(new AwsBucketFile(b.getKey())));

			return files;
		} catch (Exception e) {
			throw new AmazonS3CustomException("Erro ao consultar o conteúdo do bucket " + bucketName);
		}
	}

}
