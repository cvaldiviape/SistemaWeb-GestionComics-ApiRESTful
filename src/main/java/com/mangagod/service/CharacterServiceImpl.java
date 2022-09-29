package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.CharacterRequestDTO;
import com.mangagod.dto.response.CharacterResponseDTO;
import com.mangagod.dto.response.page.CharactersPageResponseDTO;
import com.mangagod.entity.CharacterEntity;
import com.mangagod.entity.StoryEntity;
import com.mangagod.entity.TypeCharacterEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.CharacterMapper;
import com.mangagod.repository.CharacterRepository;
import com.mangagod.repository.StoryRepository;
import com.mangagod.repository.TypeCharacterRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CharacterRepository characterRepository;
	@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private TypeCharacterRepository typeCharacterRepository;	
	@Autowired
	private CharacterMapper characterMapper;
	@Autowired
	private AppHelpers appHelpers;
		
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public CharactersPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<CharacterEntity> charactersPageable = this.characterRepository.findAll(pageable);	
		List<CharacterEntity> charactersEntity = charactersPageable.getContent();
		List<CharacterResponseDTO> charactersDTO = charactersEntity.stream().map((x) -> this.characterMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return CharactersPageResponseDTO.builder()
				.characters(charactersDTO)
				.numberPage(charactersPageable.getNumber())
				.sizePage(charactersPageable.getSize())
				.totalElements(charactersPageable.getTotalElements())
				.totalPages(charactersPageable.getTotalPages())
				.isLastPage(charactersPageable.isLast())
				.build();
	}

	@Override
	public CharacterResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		CharacterEntity entity =this.getCharacterById(id);
		return this.characterMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public CharacterResponseDTO create(CharacterRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		StoryEntity storyEntity = this.getStoryById(requestDTO.getStoryId());
		TypeCharacterEntity typeCharacterEntity = this.getTypeCharacterById(requestDTO.getTypeId());
		this.verifyNameUnique(requestDTO.getName());
		CharacterEntity entity = this.characterMapper.mapRequestToEntity(requestDTO);
		entity.setStory(storyEntity);
		entity.setType(typeCharacterEntity);
		return this.characterMapper.mapEntityToResponseDTO(this.characterRepository.save(entity));			
	}

	@Override
	public CharacterResponseDTO update(Integer id, CharacterRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		CharacterEntity dataCurrent = this.getCharacterById(id);
		StoryEntity storyEntity = this.getStoryById(requestDTO.getStoryId());
		TypeCharacterEntity typeCharacterEntity = this.getTypeCharacterById(requestDTO.getTypeId());
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		dataCurrent.setDescription(requestDTO.getDescription());
		dataCurrent.setUrlImage(requestDTO.getUrlImage());
		dataCurrent.setStory(storyEntity);
		dataCurrent.setType(typeCharacterEntity);
		return this.characterMapper.mapEntityToResponseDTO(this.characterRepository.save(dataCurrent));	
	}

	@Override
	public CharacterResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		CharacterEntity entity = this.getCharacterById(id);
		this.characterRepository.delete(entity);
		return this.characterMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public CharacterEntity getCharacterById(Integer id) {
		return this.characterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));
	}
	
	public TypeCharacterEntity getTypeCharacterById(Integer id) {
		return this.typeCharacterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de personaje", "id", id));
	}
	
	public StoryEntity getStoryById(Integer id) {
		return this.storyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Historieta", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.characterRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.characterRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
}