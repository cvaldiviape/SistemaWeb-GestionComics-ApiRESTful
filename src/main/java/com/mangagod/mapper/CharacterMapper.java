package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.CharacterRequestDTO;
import com.mangagod.dto.response.CharacterResponseDTO;
import com.mangagod.entity.CharacterEntity;

@Component
public class CharacterMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public CharacterResponseDTO mapEntityToResponseDTO(CharacterEntity entity) {
		return this.modelMapper.map(entity, CharacterResponseDTO.class);
	}
	
	public CharacterEntity mapRequestToEntity(CharacterRequestDTO requestDTO) {
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return this.modelMapper.map(requestDTO, CharacterEntity.class);
	}
	
}