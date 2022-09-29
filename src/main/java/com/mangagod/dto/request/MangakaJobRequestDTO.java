package com.mangagod.dto.request;

import javax.validation.constraints.Min;
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
public class MangakaJobRequestDTO {

	@NotNull(message = "El campo 'mangakaId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'mangakaId' debe contener un número mayor a '0'")
	private Integer mangakaId;
	@NotNull(message = "El campo 'jobId' no debe ser nulo.")
	@Min(value = 1, message = "El campo 'jobId' debe contener un número mayor a '0'")
	private Integer jobId;
		
}