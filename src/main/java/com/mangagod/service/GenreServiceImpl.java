package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.GenreRequestDTO;
import com.mangagod.dto.response.GenreResponseDTO;
import com.mangagod.dto.response.page.GenresPageResponseDTO;
import com.mangagod.entity.GenreEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.GenreMapper;
import com.mangagod.repository.GenreRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class GenreServiceImpl implements GenreService{

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private GenreMapper genreMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public GenresPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<GenreEntity> genresPageable = this.genreRepository.findAll(pageable);	
		List<GenreEntity> genresEntity = genresPageable.getContent();
		List<GenreResponseDTO> genresDTO = genresEntity.stream().map((x) -> this.genreMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return GenresPageResponseDTO.builder()
				.genres(genresDTO)
				.numberPage(genresPageable.getNumber())
				.sizePage(genresPageable.getSize())
				.totalElements(genresPageable.getTotalElements())
				.totalPages(genresPageable.getTotalPages())
				.isLastPage(genresPageable.isLast())
				.build();
	}

	@Override
	public GenreResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		GenreEntity entity =  this.getGenreById(id);
		return this.genreMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public GenreResponseDTO create(GenreRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		GenreEntity entity = this.genreMapper.mapRequestToEntity(requestDTO);
		return this.genreMapper.mapEntityToResponseDTO(this.genreRepository.save(entity));			
	}

	@Override
	public GenreResponseDTO update(Integer id, GenreRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		GenreEntity dataCurrent =  this.getGenreById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		return this.genreMapper.mapEntityToResponseDTO(this.genreRepository.save(dataCurrent));	
	}

	@Override
	public GenreResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		GenreEntity entity = this.getGenreById(id);
		this.genreRepository.delete(entity);
		return this.genreMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public GenreEntity getGenreById(Integer id) {
		return this.genreRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.genreRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.genreRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
}