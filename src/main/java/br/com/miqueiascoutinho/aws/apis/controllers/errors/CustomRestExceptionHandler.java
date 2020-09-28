package br.com.miqueiascoutinho.aws.apis.controllers.errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.miqueiascoutinho.aws.apis.exceptions.*;

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

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), errors);
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}
	
}
