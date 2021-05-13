package com.financaspessoais.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.financaspessoais.entity.TransactionsEntity;

public interface TransactionsRepository extends CrudRepository<TransactionsEntity, Integer> {

	List<TransactionsEntity> findTransactionsEntityByAccountId(Integer accountId);

}
