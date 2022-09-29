package com.mangagod.dto.request;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import com.mangagod.util.enums.Sex;
import com.mangagod.util.enums.validators.EnumValidator;

import io.swagger.annotations.ApiModelProperty;
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
public class MangakaRequestDTO {

	@NotNull(message = "El campo 'name' es obligatorio.")
	@NotBlank(message = "El campo 'name' es obligatorio.")
	@Size(max = 50, message = "El campo 'name' debe contener un maximo de 50 caracteres.")
	@Pattern(regexp= "^[a-zA-ZÀ-ÿ]+(\s?[a-zA-ZÀ-ÿ]+?)+$", message = "El campo 'name' solo admite letras." )
	private String name;
	// @EnumNamePattern(regexp = "NEW|DEFAULT")
	@ApiModelProperty("FEMENINO|MASCULINO|NO_ESPECIFICADO")
	@EnumValidator(enumClass = Sex.class, message = "valores aceptados -> FEMENINO|MASCULINO|NO_ESPECIFICADO")
	private String sex;
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy") // not working, REV
	private LocalDate birthDate;
//	@NotNull(message = "El campo 'storyJobIds' no debe ser nulo.")
//	@NotEmpty(message = "El campo 'storyJobIds' debe contene al menos 1 elemento.")
	private Set<StoryJobRequestDTO> storyJobIds;
	
}