package com.financaspessoais.dtos;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceDTO {

	@NotNull
	private double balance;

	@Getter
	@Builder.Default
	private LocalDateTime date = LocalDateTime.now();

	public String getBalance() {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat moneyBR = NumberFormat.getCurrencyInstance(localeBR);

		return moneyBR.format(balance);
	}

}
