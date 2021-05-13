package com.financaspessoais.exception;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnException {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private List<String> details;

	public ReturnException(final ErrorCode codigosDeErros, String details) {
		this.code = codigosDeErros.name();
		this.message = codigosDeErros.getMensagem();
		this.details = Collections.singletonList(details);
	}

	public ReturnException(ErrorCode codigosDeErros, List<String> details) {
		this.code = codigosDeErros.name();
		this.message = codigosDeErros.getMensagem();
		this.details = details;
	}

}
