package com.financaspessoais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financaspessoais.dtos.Categories;
import com.financaspessoais.dtos.ExtractDTO;
import com.financaspessoais.dtos.TransactionDTO;
import com.financaspessoais.dtos.TransactionsDTO;
import com.financaspessoais.entity.AccountsEntity;
import com.financaspessoais.entity.TransactionsEntity;
import com.financaspessoais.exception.AccountException;
import com.financaspessoais.exception.ErrorCode;
import com.financaspessoais.exception.TransactionException;
import com.financaspessoais.repository.TransactionsRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionsRepository transactionsRepository;

	@Autowired
	private AccountService accountService;

	public void transactionIncome(Integer accountId, TransactionDTO transaction) {
		saveTransaction(accountId, transaction, Categories.E);
	}

	public void transactionExpense(Integer accountId, TransactionDTO transaction) {
		saveTransaction(accountId, transaction, Categories.S);
	}

	private void saveTransaction(Integer accountId, TransactionDTO transaction, Categories categorie) {
		try {
			transactionsRepository
					.save(setValuesInTransactionEntity(accountService.existAccountEntity(accountId), categorie, transaction.getValue()));
		} catch (AccountException e) {
			throw new AccountException(ErrorCode.CLIENT_NO_EXIST.getMensagem());
		} catch (Exception e) {
			throw new TransactionException(ErrorCode.TRANSACTION_FAILURE.getMensagem());
		}
	}

	private TransactionsEntity setValuesInTransactionEntity(AccountsEntity account, Categories categorie, double value) {
		TransactionsEntity transactionEntity = new TransactionsEntity();
		addInAccountEntityValuesFromIncomeOrExpensive(account, categorie, value);
		transactionEntity.setAccount(account);
		transactionEntity.setCategorie(categorie);
		transactionEntity.setValue(value);
		return transactionEntity;
	}

	private void addInAccountEntityValuesFromIncomeOrExpensive(AccountsEntity account, Categories categorie, double value) {
		if (categorie.equals(Categories.E)) {
			account.setBalance(account.getBalance() + value);
			account.setIncome(account.getIncome() + value);
		} else {
			account.setBalance(account.getBalance() - value);
			account.setExpense(account.getExpense() + value);
		}
	}

	protected ExtractDTO extractTransactions(Integer accountId) {
		ExtractDTO extractDTO = new ExtractDTO();

		for (TransactionsEntity transaction : transactionsRepository.findTransactionsEntityByAccountId(accountId)) {
			extractDTO.addTransaction(TransactionsDTO.builder().value(transaction.getValue()).type(transaction.getCategorie().getName())
					.date(transaction.getCreatedAt()).build());
		}

		return extractDTO;
	}

}
