package com.financaspessoais.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.financaspessoais.dtos.Categories;
import com.financaspessoais.dtos.ExtractDTO;
import com.financaspessoais.dtos.TransactionDTO;
import com.financaspessoais.entity.AccountsEntity;
import com.financaspessoais.entity.TransactionsEntity;
import com.financaspessoais.entity.UsersEntity;
import com.financaspessoais.exception.AccountException;
import com.financaspessoais.exception.TransactionException;
import com.financaspessoais.repository.TransactionsRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	private static final Integer ACCOUNT_ID = 1;
	private static final double VALUE = 10.0;
	private static final String USER_NAME = "William Soares";
	private static final String USER_EMAIL = "william@teste.com";

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionsRepository transactionsRepository;

	@Mock
	private AccountService accountService;

	@Test
	public void transactionIncomeSuccess() {
		when(accountService.existAccountEntity(ACCOUNT_ID)).thenReturn(getAccountsEntity());
		when(transactionsRepository.save(getTransactionsEntity(Categories.E))).thenReturn(getTransactionsEntity(Categories.E));

		transactionService.transactionIncome(ACCOUNT_ID, getTransactionDTO());
	}

	@Test(expected = AccountException.class)
	public void transactionIncomeAccountException() {
		when(accountService.existAccountEntity(ACCOUNT_ID)).thenThrow(AccountException.class);

		transactionService.transactionIncome(ACCOUNT_ID, getTransactionDTO());
	}

	@Test(expected = TransactionException.class)
	public void transactionIncomeTransactionException() {
		transactionService.transactionIncome(ACCOUNT_ID, getTransactionDTO());
	}

	@Test
	public void transactionExpenseSuccess() {
		when(accountService.existAccountEntity(ACCOUNT_ID)).thenReturn(getAccountsEntity());
		when(transactionsRepository.save(getTransactionsEntity(Categories.S))).thenReturn(getTransactionsEntity(Categories.S));

		transactionService.transactionExpense(ACCOUNT_ID, getTransactionDTO());
	}

	@Test(expected = AccountException.class)
	public void transactionExpenseAccountException() {
		when(accountService.existAccountEntity(ACCOUNT_ID)).thenThrow(AccountException.class);

		transactionService.transactionExpense(ACCOUNT_ID, getTransactionDTO());
	}

	@Test(expected = TransactionException.class)
	public void transactionExpenseTransactionException() {

		transactionService.transactionExpense(ACCOUNT_ID, getTransactionDTO());
	}

	@Test
	public void extractTransactions() {
		when(transactionsRepository.findTransactionsEntityByAccountId(ACCOUNT_ID)).thenReturn(getListTransactionsEntities());

		ExtractDTO result = transactionService.extractTransactions(ACCOUNT_ID);

		assertEquals(Categories.E.getName(), result.getTransactions().get(0).getType());
	}

	private TransactionDTO getTransactionDTO() {
		return TransactionDTO.builder().value(VALUE).build();
	}

	private AccountsEntity getAccountsEntity() {
		UsersEntity user = new UsersEntity();
		user.setName(USER_NAME);
		user.setEmail(USER_EMAIL);

		AccountsEntity account = new AccountsEntity();
		account.setId(ACCOUNT_ID);
		account.setUser(user);
		account.setBalance(0);
		account.setIncome(0);
		account.setExpense(0);

		return account;
	}

	private TransactionsEntity getTransactionsEntity(Categories categorie) {
		TransactionsEntity transactionEntity = new TransactionsEntity();
		transactionEntity.setAccount(getAccountsEntity());
		transactionEntity.setCategorie(categorie);
		transactionEntity.setValue(VALUE);
		transactionEntity.setCreatedAt(LocalDateTime.now());
		return transactionEntity;
	}

	private List<TransactionsEntity> getListTransactionsEntities() {
		List<TransactionsEntity> transactions = new ArrayList<>();
		transactions.add(getTransactionsEntity(Categories.E));
		transactions.add(getTransactionsEntity(Categories.S));
		transactions.add(getTransactionsEntity(Categories.E));
		transactions.add(getTransactionsEntity(Categories.S));
		return transactions;
	}
}
