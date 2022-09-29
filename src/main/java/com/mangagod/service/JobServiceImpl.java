package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.JobRequestDTO;
import com.mangagod.dto.response.JobResponseDTO;
import com.mangagod.dto.response.page.JobsPageResponseDTO;
import com.mangagod.entity.JobEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.JobMapper;
import com.mangagod.repository.JobRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class JobServiceImpl implements JobService {
	
	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private JobMapper jobMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public JobsPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<JobEntity> jobsPageable = this.jobRepository.findAll(pageable);	
		List<JobEntity> jobsEntity = jobsPageable.getContent();
		List<JobResponseDTO> jobsDTO = jobsEntity.stream().map((x) -> this.jobMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return JobsPageResponseDTO.builder()
				.jobs(jobsDTO)
				.numberPage(jobsPageable.getNumber())
				.sizePage(jobsPageable.getSize())
				.totalElements(jobsPageable.getTotalElements())
				.totalPages(jobsPageable.getTotalPages())
				.isLastPage(jobsPageable.isLast())
				.build();
	}

	@Override
	public JobResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		JobEntity entity = this.getJobById(id);
		return this.jobMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public JobResponseDTO create(JobRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		JobEntity entity = this.jobMapper.mapRequestToEntity(requestDTO);
		return this.jobMapper.mapEntityToResponseDTO(this.jobRepository.save(entity));			
	}

	@Override
	public JobResponseDTO update(Integer id, JobRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		JobEntity dataCurrent = this.getJobById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		return this.jobMapper.mapEntityToResponseDTO(this.jobRepository.save(dataCurrent));	
	}

	@Override
	public JobResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		JobEntity entity = this.getJobById(id);
		this.jobRepository.delete(entity);
		return this.jobMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public JobEntity getJobById(Integer id) {
		return this.jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.jobRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.jobRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
}