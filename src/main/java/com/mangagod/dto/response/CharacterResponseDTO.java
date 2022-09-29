package com.mangagod.dto.response;

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
public class CharacterResponseDTO {

	private Integer id;
	private String name;
	private String description;
	private String urlImage;
	private StoryResponseDTO story;
	private TypeCharacterResponseDTO type;
	
}