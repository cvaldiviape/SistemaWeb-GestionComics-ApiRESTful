package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.CountryRequestDTO;
import com.mangagod.dto.response.CountryResponseDTO;
import com.mangagod.entity.CountryEntity;

@Component
public class CountryMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public CountryResponseDTO mapEntityToResponseDTO(CountryEntity entity) {
		return this.modelMapper.map(entity, CountryResponseDTO.class);
	}
	
	public CountryEntity mapRequestToEntity(CountryRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, CountryEntity.class);
	}
		
}