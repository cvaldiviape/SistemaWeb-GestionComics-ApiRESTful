package com.mangagod.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthRequestDTO {

	@NotNull(message = "El campo 'usernameOrEmail' es obligatorio.")
	@NotBlank(message = "El campo 'usernameOrEmail' es obligatorio.")
	private String usernameOrEmail;
	@NotNull(message = "El campo 'password' es obligatorio.")
	@NotBlank(message = "El campo 'password' es obligatorio.")
	private String password;
	@NotNull(message = "El campo 'roleId' es obligatorio.")
	@Min(value = 1, message = "El campo 'roleId' debe contener un número mayor a '0'")
	private Integer roleId;
	
}