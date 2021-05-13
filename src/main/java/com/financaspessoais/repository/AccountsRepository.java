package com.financaspessoais.repository;

import org.springframework.data.repository.CrudRepository;

import com.financaspessoais.entity.AccountsEntity;

public interface AccountsRepository extends CrudRepository<AccountsEntity, Integer> {

}
