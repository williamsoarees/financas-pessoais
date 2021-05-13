package com.financaspessoais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financaspessoais.dtos.TransactionDTO;
import com.financaspessoais.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("financaspessoais")
@Api(value = "Transaction Finanças Pessoais", tags = { "Transaction" })
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@ApiOperation(value = "Add values ​​from your account")
	@PostMapping("/v1/trasaction/{accountId}/income")
	public ResponseEntity<Void> trasactionIncome(@PathVariable("accountId") Integer accountId, @Valid @RequestBody TransactionDTO transaction) {
		transactionService.transactionIncome(accountId, transaction);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Removes values ​​from your account")
	@PostMapping("/v1/trasaction/{accountId}/expense")
	public ResponseEntity<Void> trasactionExpense(@PathVariable("accountId") Integer accountId, @Valid @RequestBody TransactionDTO transaction) {
		transactionService.transactionExpense(accountId, transaction);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
