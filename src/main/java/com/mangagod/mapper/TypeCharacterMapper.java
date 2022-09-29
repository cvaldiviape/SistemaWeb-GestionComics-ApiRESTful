package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.TypeCharacterRequestDTO;
import com.mangagod.dto.response.TypeCharacterResponseDTO;
import com.mangagod.entity.TypeCharacterEntity;

@Component
public class TypeCharacterMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public TypeCharacterResponseDTO mapEntityToResponseDTO(TypeCharacterEntity entity) {
		return this.modelMapper.map(entity, TypeCharacterResponseDTO.class);
	}
	
	public TypeCharacterEntity mapRequestToEntity(TypeCharacterRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, TypeCharacterEntity.class);
	}
	
}