package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.DemographyRequestDTO;
import com.mangagod.dto.response.DemographyResponseDTO;
import com.mangagod.entity.DemographyEntity;

@Component
public class DemographyMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public DemographyResponseDTO mapEntityToResponseDTO(DemographyEntity entity) {
		return this.modelMapper.map(entity, DemographyResponseDTO.class);
	}
	
	public DemographyEntity mapRequestToEntity(DemographyRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, DemographyEntity.class);
	}
	
}