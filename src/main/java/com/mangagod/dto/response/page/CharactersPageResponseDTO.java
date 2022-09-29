package com.mangagod.dto.response.page;

import java.util.List;

import com.mangagod.dto.response.CharacterResponseDTO;
import com.mangagod.dto.response.page.base.PageBase;

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
public class CharactersPageResponseDTO extends PageBase {

	private List<CharacterResponseDTO> characters;
	
}