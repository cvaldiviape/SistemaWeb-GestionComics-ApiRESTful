package com.mangagod.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.JobRequestDTO;
import com.mangagod.dto.response.JobResponseDTO;
import com.mangagod.entity.JobEntity;

@Component
public class JobMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	// ---------------------------------------------------------- modelMapper --------------------------------------------------------- //
	public JobResponseDTO mapEntityToResponseDTO(JobEntity entity) {
		return this.modelMapper.map(entity, JobResponseDTO.class);
	}
	
	public JobEntity mapRequestToEntity(JobRequestDTO requestDTO) {
		return this.modelMapper.map(requestDTO, JobEntity.class);
	}
	
}