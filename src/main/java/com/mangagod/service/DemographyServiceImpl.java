package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.DemographyRequestDTO;
import com.mangagod.dto.response.DemographyResponseDTO;
import com.mangagod.dto.response.page.DemographiesPageResponseDTO;
import com.mangagod.entity.DemographyEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.DemographyMapper;
import com.mangagod.repository.DemographyRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class DemographyServiceImpl implements DemographyService{

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private DemographyRepository demographyRepository;
	@Autowired
	private DemographyMapper demographyMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public DemographiesPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<DemographyEntity> demographiesPageable = this.demographyRepository.findAll(pageable);	
		List<DemographyEntity> demographiesEntity = demographiesPageable.getContent();
		List<DemographyResponseDTO> demographiesDTO = demographiesEntity.stream().map((x) -> this.demographyMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return DemographiesPageResponseDTO.builder()
				.demogrhapies(demographiesDTO)
				.numberPage(demographiesPageable.getNumber())
				.sizePage(demographiesPageable.getSize())
				.totalElements(demographiesPageable.getTotalElements())
				.totalPages(demographiesPageable.getTotalPages())
				.isLastPage(demographiesPageable.isLast())
				.build();
	}

	@Override
	public DemographyResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		DemographyEntity entity = this.getDemographyById(id);
		return this.demographyMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public DemographyResponseDTO create(DemographyRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		DemographyEntity entity = this.demographyMapper.mapRequestToEntity(requestDTO);
		return this.demographyMapper.mapEntityToResponseDTO(this.demographyRepository.save(entity));			
	}

	@Override
	public DemographyResponseDTO update(Integer id, DemographyRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		DemographyEntity dataCurrent = this.getDemographyById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		return this.demographyMapper.mapEntityToResponseDTO(this.demographyRepository.save(dataCurrent));	
	}

	@Override
	public DemographyResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		DemographyEntity entity = this.getDemographyById(id);
		this.demographyRepository.delete(entity);
		return this.demographyMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public DemographyEntity getDemographyById(Integer id) {
		return this.demographyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Demogragía", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.demographyRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.demographyRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
		
}