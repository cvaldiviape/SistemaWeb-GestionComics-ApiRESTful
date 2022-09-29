package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.RoleRequestDTO;
import com.mangagod.dto.response.RoleResponseDTO;
import com.mangagod.entity.RoleEntity;

@Component
public class RoleMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public RoleResponseDTO mapEntityToResponseDTO(RoleEntity entity) {
		return this.modelMapper.map(entity, RoleResponseDTO.class);
	}
	
	public RoleEntity mapRequestToEntity(RoleRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, RoleEntity.class);
	}
	
}