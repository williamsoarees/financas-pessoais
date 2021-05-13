package com.financaspessoais.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class AccountsEntity {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne
	@Cascade(CascadeType.ALL)
	private UsersEntity user;

	@Column(name = "balance", nullable = false)
	private double balance;

	@Column(name = "income", nullable = false)
	private double income;

	@Column(name = "expense", nullable = false)
	private double expense;
}
