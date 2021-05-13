package com.financaspessoais.handler;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.financaspessoais.exception.AccountException;
import com.financaspessoais.exception.ErrorCode;
import com.financaspessoais.exception.ReturnException;
import com.financaspessoais.exception.TransactionException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		ReturnException respostaExcecao = new ReturnException(status.name(), status.getReasonPhrase(), Arrays.asList(ex.getLocalizedMessage()));

		return ResponseEntity.status(status).body(respostaExcecao);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		ReturnException respostaExcecao = new ReturnException(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());

		request.getDescription(false);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respostaExcecao);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

		ReturnException respostaExcecao = new ReturnException(ErrorCode.INVALID_REQUEST, ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaExcecao);

	}

	@ExceptionHandler(AccountException.class)
	public ResponseEntity<Object> handleAccountException(AccountException ex) {

		ReturnException returnException = new ReturnException(ErrorCode.CLIENT_NO_EXIST, ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnException);

	}

	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<Object> handleTransactionException(TransactionException ex) {

		ReturnException returnException = new ReturnException(ErrorCode.TRANSACTION_FAILURE, ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnException);

	}

}
