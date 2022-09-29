package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.GenreRequestDTO;
import com.mangagod.dto.response.GenreResponseDTO;
import com.mangagod.entity.GenreEntity;

@Component
public class GenreMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public GenreResponseDTO mapEntityToResponseDTO(GenreEntity entity) {
		return this.modelMapper.map(entity, GenreResponseDTO.class);
	}
	
	public GenreEntity mapRequestToEntity(GenreRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, GenreEntity.class);
	}
	
}