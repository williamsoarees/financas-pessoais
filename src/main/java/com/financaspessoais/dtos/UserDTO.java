package com.financaspessoais.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

	@NotBlank(message = "Informe o name")
	private String name;

	@Email(message = "Este e-mail não é válido")
	private String email;

}
