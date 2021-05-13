package com.financaspessoais.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.financaspessoais.dtos.Categories;

import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class TransactionsEntity {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	private AccountsEntity account;

	@Column(name = "value", nullable = false)
	private double value;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "categorie", nullable = false)
	private Categories categorie;

}
