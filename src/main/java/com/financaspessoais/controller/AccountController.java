package com.financaspessoais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financaspessoais.dtos.AccountDTO;
import com.financaspessoais.dtos.BalanceDTO;
import com.financaspessoais.dtos.ExtractDTO;
import com.financaspessoais.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("financaspessoais")
@Api(value = "Account Finanças Pessoais", tags = { "Account" })
public class AccountController {

	@Autowired
	AccountService accountService;

	@ApiOperation(value = "Create an account")
	@PostMapping("/v1/account")
	public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO account) {
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
	}

	@ApiOperation(value = "Checks your account balance")
	@GetMapping("/v1/{accountId}/balance")
	public ResponseEntity<BalanceDTO> accountBalance(@PathVariable("accountId") Integer accountId) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.accountBalance(accountId));
	}

	@ApiOperation(value = "Checks your account extract")
	@GetMapping("/v1/{accountId}/extract")
	public ResponseEntity<ExtractDTO> accountExtract(@PathVariable("accountId") Integer accountId) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.accountExtract(accountId));
	}

}
