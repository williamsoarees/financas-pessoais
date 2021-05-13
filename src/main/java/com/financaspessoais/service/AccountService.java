package com.financaspessoais.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financaspessoais.dtos.AccountDTO;
import com.financaspessoais.dtos.BalanceDTO;
import com.financaspessoais.dtos.ExtractDTO;
import com.financaspessoais.entity.AccountsEntity;
import com.financaspessoais.exception.AccountException;
import com.financaspessoais.exception.ErrorCode;
import com.financaspessoais.repository.AccountsRepository;

@Service
public class AccountService {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ModelMapper mapper;

	public AccountDTO createAccount(AccountDTO account) {
		return mapper.map(accountsRepository.save(mapper.map(account, AccountsEntity.class)), AccountDTO.class);
	}

	public BalanceDTO accountBalance(Integer accountId) {
		return mapper.map(existAccountEntity(accountId), BalanceDTO.class);
	}

	public ExtractDTO accountExtract(Integer accountId) {
		return transactionService.extractTransactions(existAccountEntity(accountId).getId());
	}

	protected AccountsEntity existAccountEntity(Integer accountId) {
		Optional<AccountsEntity> account = accountsRepository.findById(accountId);

		if (account == null || !account.isPresent()) {
			throw new AccountException(ErrorCode.CLIENT_NO_EXIST.getMensagem());
		}

		return account.get();
	}

}
