package br.com.miqueiascoutinho.aws.apis.exceptions;

import lombok.Getter;

@Getter
public class AmazonS3CustomException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AmazonS3CustomException(String ex) {
		super(ex);
	}

	public AmazonS3CustomException(String ex, Throwable e) {
		super(ex, e);
	}

}
