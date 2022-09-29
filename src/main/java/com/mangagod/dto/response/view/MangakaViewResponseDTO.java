package com.mangagod.dto.response.view;

import java.time.LocalDate;
import com.mangagod.util.enums.Sex;
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
public class MangakaViewResponseDTO {

	private Integer id;
	private String name;
	private Sex sex;
	private LocalDate birthDate;
	
}