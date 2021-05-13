package com.financaspessoais.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.financaspessoais.dtos.AccountDTO;
import com.financaspessoais.dtos.BalanceDTO;
import com.financaspessoais.dtos.Categories;
import com.financaspessoais.dtos.ExtractDTO;
import com.financaspessoais.dtos.TransactionsDTO;
import com.financaspessoais.dtos.UserDTO;
import com.financaspessoais.entity.AccountsEntity;
import com.financaspessoais.entity.UsersEntity;
import com.financaspessoais.exception.AccountException;
import com.financaspessoais.repository.AccountsRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {

	private static final Integer ACCOUNT_ID = 1;
	private static final String USER_NAME = "William Soares";
	private static final String USER_EMAIL = "william@teste.com";
	private static final double ACCOUNT_INCOME = 362.93;
	private static final String ACCOUNT_INCOME_RETURN = "R$ 362,93";
	private static final double ACCOUNT_EXPENSE = 274.14;
	private static final String ACCOUNT_EXPENSE_RETURN = "R$ 274,14";
	private static final double BALANCE = 637.07;
	private static final String BALANCE_RETURN = "R$ 637,07";

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountsRepository accountsRepository;

	@Mock
	private TransactionService transactionService;

	@Mock
	private ModelMapper modelMapper;

	@Test
	public void createAccountSuccess() {
		AccountDTO account = getAccountDTO();
		when(modelMapper.map(getAccountDTO(), AccountsEntity.class)).thenReturn(getAccountsEntity());
		when(accountsRepository.save(getAccountsEntity())).thenReturn(getAccountsEntity());
		when(modelMapper.map(getAccountsEntity(), AccountDTO.class)).thenReturn(getAccountDTO());

		AccountDTO result = accountService.createAccount(account);

		assertEquals(new Integer(1), result.getIdAccounts());
	}

	@Test
	public void createAccountNullAccount() {
		AccountDTO account = getAccountDTO();
		when(modelMapper.map(getAccountDTO(), AccountsEntity.class)).thenReturn(getAccountsEntity());
		when(modelMapper.map(getAccountsEntity(), AccountDTO.class)).thenReturn(getAccountDTO());

		AccountDTO result = accountService.createAccount(account);
		assertNull(result);
	}

	@Test
	public void accountBalanceSuccess() {
		Optional<AccountsEntity> accountsEntity = Optional.of(getAccountsEntity());
		accountsEntity.get().setBalance(200);

		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(accountsEntity);
		when(modelMapper.map(accountsEntity.get(), BalanceDTO.class)).thenReturn(BalanceDTO.builder().balance(BALANCE).build());

		BalanceDTO result = accountService.accountBalance(ACCOUNT_ID);

		assertEquals(BALANCE_RETURN, result.getBalance());
	}

	@Test(expected = AccountException.class)
	public void accountBalanceErrorAccountNoExists() {
		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());
		accountService.accountBalance(ACCOUNT_ID);
	}

	@Test(expected = AccountException.class)
	public void accountBalanceErrorAccountNull() {
		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(null);
		accountService.accountBalance(ACCOUNT_ID);
	}

	@Test
	public void accountExtractSuccess() {
		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(getAccountsEntity()));
		when(transactionService.extractTransactions(ACCOUNT_ID)).thenReturn(getExtractDTO());

		ExtractDTO result = accountService.accountExtract(ACCOUNT_ID);

		assertEquals(ACCOUNT_INCOME_RETURN, result.getTransactions().get(0).getValue());
		assertEquals(ACCOUNT_EXPENSE_RETURN, result.getTransactions().get(1).getValue());
	}

	@Test
	public void accountExtractEmptyTransactions() {
		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(getAccountsEntity()));
		when(transactionService.extractTransactions(ACCOUNT_ID)).thenReturn(new ExtractDTO());

		ExtractDTO result = accountService.accountExtract(ACCOUNT_ID);

		assertTrue(result.getTransactions().isEmpty());
	}

	@Test(expected = AccountException.class)
	public void accountExtractAccountNoExists() {
		when(accountsRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());
		accountService.accountExtract(ACCOUNT_ID);
	}

	private AccountDTO getAccountDTO() {
		return AccountDTO.builder().idAccounts(ACCOUNT_ID).user(getUserDTO()).build();
	}

	private UserDTO getUserDTO() {
		return new UserDTO(USER_NAME, USER_EMAIL);
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

	private ExtractDTO getExtractDTO() {
		ExtractDTO extractDTO = new ExtractDTO();
		extractDTO
				.addTransaction(TransactionsDTO.builder().value(ACCOUNT_INCOME).date(LocalDateTime.now()).type(Categories.E.getName()).build());
		extractDTO
				.addTransaction(TransactionsDTO.builder().value(ACCOUNT_EXPENSE).date(LocalDateTime.now()).type(Categories.S.getName()).build());
		return extractDTO;
	}

}
