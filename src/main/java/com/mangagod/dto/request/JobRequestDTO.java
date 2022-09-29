package com.mangagod.dto.request;

import javax.validation.constraints.NotBlank;
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
public class JobRequestDTO {

	@NotNull(message = "El campo 'name' es obligatorio.")
	@NotBlank(message = "El campo 'name' es obligatorio.")
	@Size(max = 50, message = "El campo 'name' debe contener un maximo de 50 caracteres.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ]+(-|_)?([a-zA-ZÀ-ÿ]+?)+$", message = "El campo 'name' solo admite letras y guion entre 2 palabras.")
	private String name;
	
}