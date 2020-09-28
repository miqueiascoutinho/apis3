package br.com.miqueiascoutinho.aws.apis.controllers.errors;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiError {

	private int code;
	private List<String> messages;

	public ApiError(final int code, final List<String> messages) {
		this.code = code;
		this.messages = messages;
	}

	public ApiError(final int code, final String message) {
		this.code = code;
		messages = Arrays.asList(message);
	}
}
