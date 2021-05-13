package com.financaspessoais.dtos;

import lombok.Getter;

@Getter
public enum Categories {

	E("Entrada"), S("Saida");

	private String name;

	private Categories(String name) {
		this.name = name;
	}

	public static Categories parse(String name) {
		for (Categories categories : Categories.values()) {
			if (name.equals(categories.getName())) {
				return categories;
			}
		}

		return null;
	}
}
