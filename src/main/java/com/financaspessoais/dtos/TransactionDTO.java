package com.financaspessoais.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

	@NotNull
	@Range(min = 1, message = "O valor deve ser positivo e no mínimo 1")
	private double value;

}
