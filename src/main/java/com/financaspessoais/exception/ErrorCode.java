package com.financaspessoais.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR("Internal server error"), INVALID_REQUEST("Invalid request"), CLIENT_NO_EXIST("Este cliente não existe!"),
	TRANSACTION_FAILURE("A transação não foi realizada");

	private final String mensagem;

}
