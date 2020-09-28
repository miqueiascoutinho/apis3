package br.com.miqueiascoutinho.aws.apis.exceptions;

import lombok.Getter;

@Getter
public class AmazonS3CustomNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AmazonS3CustomNotFoundException(String ex) {
		super(ex);
	}

	public AmazonS3CustomNotFoundException(String ex, Throwable e) {
		super(ex, e);
	}

}
