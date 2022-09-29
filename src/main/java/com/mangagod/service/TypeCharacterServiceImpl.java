package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.TypeCharacterRequestDTO;
import com.mangagod.dto.response.TypeCharacterResponseDTO;
import com.mangagod.dto.response.page.TypeCharactersPageResponseDTO;
import com.mangagod.entity.TypeCharacterEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.TypeCharacterMapper;
import com.mangagod.repository.TypeCharacterRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class TypeCharacterServiceImpl implements TypeCharacterService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private TypeCharacterRepository typeCharacterRepository;
	@Autowired
	private TypeCharacterMapper typeCharacterMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public TypeCharactersPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<TypeCharacterEntity> typeCharactersPageable = this.typeCharacterRepository.findAll(pageable);	
		List<TypeCharacterEntity> typeCharactersEntity = typeCharactersPageable.getContent();
		List<TypeCharacterResponseDTO> typeCharacteresDTO = typeCharactersEntity.stream().map((x) -> this.typeCharacterMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
			
		return TypeCharactersPageResponseDTO.builder()
				.typeCharacters(typeCharacteresDTO)
				.numberPage(typeCharactersPageable.getNumber())
				.sizePage(typeCharactersPageable.getSize())
				.totalElements(typeCharactersPageable.getTotalElements())
				.totalPages(typeCharactersPageable.getTotalPages())
				.isLastPage(typeCharactersPageable.isLast())
				.build();
	}

	@Override
	public TypeCharacterResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		TypeCharacterEntity entity = this.getTypeCharacterById(id);
		return this.typeCharacterMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public TypeCharacterResponseDTO create(TypeCharacterRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		TypeCharacterEntity entity = this.typeCharacterMapper.mapRequestToEntity(requestDTO);
		return this.typeCharacterMapper.mapEntityToResponseDTO(this.typeCharacterRepository.save(entity));
	}

	@Override
	public TypeCharacterResponseDTO update(Integer id, TypeCharacterRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		TypeCharacterEntity dataCurrent = this.getTypeCharacterById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName().trim());
		return this.typeCharacterMapper.mapEntityToResponseDTO(this.typeCharacterRepository.save(dataCurrent));	
	}

	@Override
	public TypeCharacterResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		TypeCharacterEntity entity = this.getTypeCharacterById(id);
		this.typeCharacterRepository.delete(entity);
		return this.typeCharacterMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public TypeCharacterEntity getTypeCharacterById(Integer id) {
		return this.typeCharacterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de personaje", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.typeCharacterRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.typeCharacterRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
		
}