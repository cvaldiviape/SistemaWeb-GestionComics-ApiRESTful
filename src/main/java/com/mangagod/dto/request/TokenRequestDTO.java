package com.mangagod.dto.request;

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
public class TokenRequestDTO {

	@NotNull(message = "El campo 'tokenAccess' es obligatorio.")
	@NotBlank(message = "El campo 'tokenAccess' es obligatorio.")
	private String tokenAccess;
		
}