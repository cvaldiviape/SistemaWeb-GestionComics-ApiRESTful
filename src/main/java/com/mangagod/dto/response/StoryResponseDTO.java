package com.mangagod.dto.response;

import java.util.Set;
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
public class StoryResponseDTO {

	private Integer Id;
	private String title;
	private Short year;
	private String synopsis;
	private Boolean state;
	private String urlImage;
	private Boolean adaptationAnime;
	private Double price;
	private CountryResponseDTO country;
	private DemographyResponseDTO demography;
	private CategoryResponseDTO category;
	private Set<GenreResponseDTO> genres;
	private Set<MangakasJobsResponseDTO> mangakasJobs;
	
}