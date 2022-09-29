package com.mangagod.dto.response.view;

import com.mangagod.dto.response.CategoryResponseDTO;
import com.mangagod.dto.response.CountryResponseDTO;
import com.mangagod.dto.response.DemographyResponseDTO;
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
public class StoryViewResponseDTO {

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

}