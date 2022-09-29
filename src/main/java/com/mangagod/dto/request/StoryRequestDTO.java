package com.mangagod.dto.request;

import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class StoryRequestDTO {

	@NotNull(message = "El campo 'title' no debe ser nulo.")
	@NotBlank(message = "El campo 'title' es obligatorio.")
	@Size( max = 50, message = "El campo 'title' debe contener maximo de 50 caracteres.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ0-9¿?,.!¡:\\-\\#$\\(\\)]+(\\s?[a-zA-ZÀ-ÿ0-9¿?,.!¡:\\-\\#$\\(\\)]+?)+$", message = "El campo 'title' no admite caracteres extraños.")
	private String title;
	@NotNull(message = "El campo 'year' no debe ser nulo.")
	@Min(value = 1900, message = "Año mínimo aceptado es 1900.")
	@Max(value = 2050, message = "Año máximo aceptado es 2050.")
	private Short year;
	@NotNull(message = "El campo 'synopsis' no debe ser nulo.")
	@NotBlank(message = "El campo 'synopsis' es obligatorio.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ0-9¿?,.!¡:\\-\\#$\\(\\)]+(\\s?[a-zA-ZÀ-ÿ0-9¿?,.!¡:\\-\\#$\\(\\)]+?)+$", message = "El campo 'synopsis' no admite caracteres extraños.")
	private String synopsis;
	@NotNull(message = "El campo 'state' no debe ser nulo.")
	private Boolean state;
	@NotNull(message = "El campo 'urlImage' no debe ser nulo.")
	@NotBlank(message = "El campo 'urlImage' es obligatorio.")
	@Pattern(regexp= "^(ftp|http|https):\\/\\/[^ \"]+$", message = "El campo 'urlImage' debe ser una url valida.")
	private String urlImage;
	@NotNull(message = "El campo 'adaptationAnime' no debe ser nulo.")
	private Boolean adaptationAnime;
	private Double price;
	@NotNull(message = "El campo 'countryId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'countryId' debe contener un número mayor a '0'")
	private Integer countryId;
	@NotNull(message = "El campo 'demographyId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'demographyId' debe contener un número mayor a '0'")
	private Integer demographyId;
	@NotNull(message = "El campo 'categoryId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'categoryId' debe contener un número mayor a '0'")
	private Integer categoryId;
	@NotNull(message = "El campo 'genreIds' es obligatorio.")
	@NotEmpty(message = "El campo 'genreIds' debe contene al menos 1 elemento.")
	private Set<Integer> genreIds;
//	@NotNull(message = "El campo 'mangakaJobIds' no debe ser nulo.")
//	@NotEmpty(message = "El campo 'mangakaJobIds' debe contene al menos 1 elemento.")
	private Set<MangakaJobRequestDTO> mangakaJobIds;
	
}