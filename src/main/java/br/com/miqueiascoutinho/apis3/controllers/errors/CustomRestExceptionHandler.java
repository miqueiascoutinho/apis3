package br.com.miqueiascoutinho.apis3.controllers.errors;

import br.com.miqueiascoutinho.apis3.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ AmazonS3CustomException.class })
	public ResponseEntity<Object> handleAmazonS3Exception(final AmazonS3CustomException ex,
			final WebRequest request) {
		logger.error(ex.getClass().getName());

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ AmazonS3CustomNotFoundException.class })
	public ResponseEntity<Object> handleAmazonS3NotFoundException(final AmazonS3CustomNotFoundException ex,
			final WebRequest request) {
		logger.error(ex.getClass().getName());

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
}
