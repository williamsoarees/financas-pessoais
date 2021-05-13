package com.financaspessoais.repository;

import org.springframework.data.repository.CrudRepository;

import com.financaspessoais.entity.UsersEntity;

public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {

}
