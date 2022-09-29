package com.mangagod.dto.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class UserUpdateRequestDTO {

	@NotNull(message = "El campo 'name' es obligatorio.")
	@NotBlank(message = "El campo 'name' es obligatorio.")
	@Size(min = 2, max = 50, message = "El campo 'username' debe contener un minimo de 2 caracteres y maximo de 50 caracteres.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ0-9]+$", message = "El campo 'name' solo admite letras o números." )
	private String username;
	@NotNull(message = "El campo 'email' es obligatorio.")
	@NotBlank(message = "El campo 'email' es obligatorio.")
	@Email(message = "El campo 'email' no es valido, ejm: nombre@ejemplo.com.")
	@Size(max = 50, message = "El campo 'email' debe un maximo de 50 caracteres.")
	private String email;
	@NotNull(message = "El campo 'roleIds' es obligatorio.")
	@NotEmpty(message = "El campo 'roleIds' debe contene al menos 1 elemento.")
	private Set<Integer> roleIds;
	
}