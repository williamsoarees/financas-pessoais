package com.financaspessoais.dtos;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsDTO {

	@Getter(AccessLevel.NONE)
	private double value;

	private LocalDateTime date;

	private String type;

	public String getValue() {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat moneyBR = NumberFormat.getCurrencyInstance(localeBR);

		return moneyBR.format(value);
	}

}
