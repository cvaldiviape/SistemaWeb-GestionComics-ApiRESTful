package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.MangakaRequestDTO;
import com.mangagod.dto.response.MangakaResponseDTO;
import com.mangagod.dto.response.view.MangakaViewResponseDTO;
import com.mangagod.entity.MangakaEntity;

@Component
public class MangakaMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public MangakaViewResponseDTO mapEntityToViewResponseDTO(MangakaEntity entity) {
		return this.modelMapper.map(entity, MangakaViewResponseDTO.class);
	}
	
	public MangakaResponseDTO mapEntityToResponseDTO(MangakaEntity entity) {
		return this.modelMapper.map(entity, MangakaResponseDTO.class);
	}
	
	public MangakaEntity mapRequestToEntity(MangakaRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, MangakaEntity.class);
	}
		
}