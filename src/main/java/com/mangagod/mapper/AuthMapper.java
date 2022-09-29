package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.response.UserAuthResponseDTO;
import com.mangagod.entity.UserEntity;

@Component
public class AuthMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public UserAuthResponseDTO mapEntityToResponse(UserEntity entity) {
		return this.modelMapper.map(entity, UserAuthResponseDTO.class);
	}

}