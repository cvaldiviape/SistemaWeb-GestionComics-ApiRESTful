package com.mangagod.dto.response;

import java.time.LocalDate;
import java.util.Set;
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
public class MangakaResponseDTO {

	private Integer id;
	private String name;
	private Sex sex;
	private LocalDate birthDate;
	private Set<StoriesJobsResponseDTO> storiesJobs;
	
}