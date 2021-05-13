package com.financaspessoais.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtractDTO {

	@Builder.Default
	private List<TransactionsDTO> transactions = new ArrayList<>();

	public void addTransaction(TransactionsDTO transaction) {
		transactions.add(transaction);
	}

}
