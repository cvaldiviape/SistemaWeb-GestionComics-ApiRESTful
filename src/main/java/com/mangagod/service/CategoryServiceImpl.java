package com.mangagod.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.CategoryRequestDTO;
import com.mangagod.dto.response.CategoryResponseDTO;
import com.mangagod.dto.response.page.CategoriesPageResponseDTO;
import com.mangagod.entity.CategoryEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.CategoryMapper;
import com.mangagod.repository.CategoryRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private AppHelpers appHelpers;	
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public CategoriesPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<CategoryEntity> categoriesPageable = this.categoryRepository.findAll(pageable);	
		List<CategoryEntity> categoriesEntity = categoriesPageable.getContent();
		List<CategoryResponseDTO> categoriesDTO = categoriesEntity.stream().map((x) -> this.categoryMapper.mapEntityToResponseDTO(x)).collect(Collectors.toList());	
		return CategoriesPageResponseDTO.builder()
				.categories(categoriesDTO)
				.numberPage(categoriesPageable.getNumber())
				.sizePage(categoriesPageable.getSize())
				.totalElements(categoriesPageable.getTotalElements())
				.totalPages(categoriesPageable.getTotalPages())
				.isLastPage(categoriesPageable.isLast())
				.build();
	}

	@Override
	public CategoryResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		CategoryEntity entity = this.getCategoryById(id);
		return this.categoryMapper.mapEntityToResponseDTO(entity);
	}

	@Override
	public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName());
		CategoryEntity entity = this.categoryMapper.mapRequestToEntity(requestDTO);
		return  this.categoryMapper.mapEntityToResponseDTO(this.categoryRepository.save(entity));			
	}

	@Override
	public CategoryResponseDTO update(Integer id, CategoryRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		CategoryEntity dataCurrent = this.getCategoryById(id);
		this.verifyNameUnique(requestDTO.getName(), dataCurrent.getName());
		dataCurrent.setName(requestDTO.getName());
		return this.categoryMapper.mapEntityToResponseDTO(this.categoryRepository.save(dataCurrent));	
	}

	@Override
	public CategoryResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		CategoryEntity entity = this.getCategoryById(id);
		this.categoryRepository.delete(entity);
		return this.categoryMapper.mapEntityToResponseDTO(entity);
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public CategoryEntity getCategoryById(Integer id) {
		return this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existName = this.categoryRepository.existsByName(name);
		if(existName) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existName = this.categoryRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existName && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El name " + name + " ya existe.");
		}
	}
	
}