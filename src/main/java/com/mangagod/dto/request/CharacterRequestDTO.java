package com.mangagod.dto.request;

import javax.validation.constraints.Min;
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
public class CharacterRequestDTO {

	@NotNull(message = "El campo 'name' no debe ser nulo.")
	@Size(max = 50, message = "El campo 'name' debe contener un maximo de 50 caracteres.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ]+(\s?[a-zA-ZÀ-ÿ]+?)+$", message = "El campo 'name' solo admite letras.")
	private String name;
	@NotNull(message = "El campo 'description' no debe ser nulo.")
	@NotBlank(message = "El campo 'description' es obligatorio.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ]+(\s?[a-zA-ZÀ-ÿ]+?)+$", message = "El campo 'description' solo admite letras.")
	private String description;
	@NotNull(message = "El campo 'urlImage'no debe ser nulo.")
	@NotBlank(message = "El campo 'urlImage' es obligatorio.")
	@Pattern(regexp= "^(ftp|http|https):\\/\\/[^ \"]+$", message = "El campo 'urlImage' debe ser una url valida.")
	private String urlImage;
	@NotNull(message = "El campo 'storyId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'storyId' debe contener un número mayor a '0'")
	private Integer storyId;
	@NotNull(message = "El campo 'typeId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'typeId' debe contener un número mayor a '0'")
	private Integer typeId;

}