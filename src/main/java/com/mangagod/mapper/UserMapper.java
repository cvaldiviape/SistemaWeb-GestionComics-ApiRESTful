package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.UserCreateRequestDTO;
import com.mangagod.dto.response.UserResponseDTO;
import com.mangagod.dto.response.view.UserViewResponseDTO;
import com.mangagod.entity.UserEntity;

@Component
public class UserMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public UserResponseDTO mapEntityToResponseDTO(UserEntity userEntity) {
		return this.modelMapper.map(userEntity, UserResponseDTO.class);
	}
	
	public UserViewResponseDTO mapEntityToViewResponseDTO(UserEntity userEntity) {
		return this.modelMapper.map(userEntity, UserViewResponseDTO.class);
	}
	
	public UserEntity mapRequestToEntity(UserCreateRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, UserEntity.class);
	}
		
}