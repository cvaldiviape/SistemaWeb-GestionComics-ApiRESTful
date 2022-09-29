package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.CategoryRequestDTO;
import com.mangagod.dto.response.CategoryResponseDTO;
import com.mangagod.entity.CategoryEntity;;

@Component
public class CategoryMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public CategoryResponseDTO mapEntityToResponseDTO(CategoryEntity entity) {
		return this.modelMapper.map(entity, CategoryResponseDTO.class);
	}
	
	public CategoryEntity mapRequestToEntity(CategoryRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, CategoryEntity.class);
	}
	
}