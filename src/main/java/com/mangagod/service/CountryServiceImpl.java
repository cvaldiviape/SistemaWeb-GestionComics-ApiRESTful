package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.CountryRequestDTO;
import com.mangagod.dto.response.CountryResponseDTO;
import com.mangagod.dto.response.page.CountriesPageResponseDTO;
import com.mangagod.entity.CountryEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.CountryMapper;
import com.mangagod.repository.CountryRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
	
	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private CountryMapper countryMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public CountriesPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<CountryEntity> countriesPageable = this.countryRepository.findAll(pageable);	
		List<CountryEntity> countriesEntity = countriesPageable.getContent();
		List<CountryResponseDTO> countriesDTO = countriesEntity.stream().map((x) -> this.countryMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return CountriesPageResponseDTO.builder()
				.countries(countriesDTO)
				.numberPage(countriesPageable.getNumber())
				.sizePage(countriesPageable.getSize())
				.totalElements(countriesPageable.getTotalElements())
				.totalPages(countriesPageable.getTotalPages())
				.isLastPage(countriesPageable.isLast())
				.build();
	}
	
	@Override
	public CountryResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		CountryEntity entity = this.getCountryById(id);
		return this.countryMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public CountryResponseDTO create(CountryRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		CountryEntity entity = this.countryMapper.mapRequestToEntity(requestDTO);
		return this.countryMapper.mapEntityToResponseDTO(this.countryRepository.save(entity));			
	}

	@Override
	public CountryResponseDTO update(Integer id, CountryRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		CountryEntity dataCurrent = this.getCountryById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		return this.countryMapper.mapEntityToResponseDTO(this.countryRepository.save(dataCurrent));	
	}

	@Override
	public CountryResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		CountryEntity entity =this.getCountryById(id);
		this.countryRepository.delete(entity);
		return this.countryMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public CountryEntity getCountryById(Integer id) {
		return this.countryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pais", "id", id));
	}
		
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.countryRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.countryRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
}