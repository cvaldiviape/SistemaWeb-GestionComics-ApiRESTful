package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.StoryRequestDTO;
import com.mangagod.dto.response.StoryResponseDTO;
import com.mangagod.dto.response.view.StoryViewResponseDTO;
import com.mangagod.entity.StoryEntity;

@Component
public class StoryMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public StoryResponseDTO mapEntityToResponseDTO(StoryEntity entity) {
		return this.modelMapper.map(entity, StoryResponseDTO.class);
	}

	public StoryViewResponseDTO mapEntityToViewResponseDTO(StoryEntity entity) {
		return this.modelMapper.map(entity, StoryViewResponseDTO.class);
	}
	
	public StoryEntity mapRequestToEntity(StoryRequestDTO requestDTO) {
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return this.modelMapper.map(requestDTO, StoryEntity.class);
	}
	
}