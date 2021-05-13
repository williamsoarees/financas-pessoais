package com.financaspessoais.dtos;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

	private Integer idAccounts;

	@Valid
	private UserDTO user;

	@Builder.Default
	private double balance = 0.0;

}
