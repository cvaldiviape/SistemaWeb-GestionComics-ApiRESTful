package com.mangagod.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.MangakaJobRequestDTO;
import com.mangagod.dto.request.StoryRequestDTO;
import com.mangagod.dto.response.MangakasJobsResponseDTO;
import com.mangagod.dto.response.StoryResponseDTO;
import com.mangagod.dto.response.page.StoriesPageResponseDTO;
import com.mangagod.dto.response.view.StoryViewResponseDTO;
import com.mangagod.entity.CategoryEntity;
import com.mangagod.entity.CountryEntity;
import com.mangagod.entity.DemographyEntity;
import com.mangagod.entity.GenreEntity;
import com.mangagod.entity.JobEntity;
import com.mangagod.entity.MangakaEntity;
import com.mangagod.entity.StoryEntity;
import com.mangagod.entity.StoryMangakaEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.StoryMapper;
import com.mangagod.repository.CategoryRepository;
import com.mangagod.repository.CountryRepository;
import com.mangagod.repository.DemographyRepository;
import com.mangagod.repository.GenreRepository;
import com.mangagod.repository.JobRepository;
import com.mangagod.repository.MangakaRepository;
import com.mangagod.repository.StoryMangakaRepository;
import com.mangagod.repository.StoryRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class StoryServiceImpl implements StoryService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private DemographyRepository demographyRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private MangakaRepository mangakaRepository;
	@Autowired
	private StoryMangakaRepository storyMangakaRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private StoryMapper storyMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	@Override
	public StoriesPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<StoryEntity> storiesPageable = this.storyRepository.findAll(pageable);
		List<StoryEntity> storiesEntity = storiesPageable.getContent();
		List<StoryViewResponseDTO> storiesDTO = storiesEntity.stream().map((x) -> this.storyMapper.mapEntityToViewResponseDTO(x)).collect(Collectors.toList());	
		
		return StoriesPageResponseDTO.builder()
				.stories(storiesDTO)
				.numberPage(storiesPageable.getNumber())
				.sizePage(storiesPageable.getSize())
				.totalElements(storiesPageable.getTotalElements())
				.totalPages(storiesPageable.getTotalPages())
				.isLastPage(storiesPageable.isLast())
				.build();
	}

	@Override
	public StoryResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		StoryEntity entity = this.getStoryById(id);
		// seteando "response".
		return this.getFullResponse(entity, entity.getStoriesMangakas());
	}

	@Override
	public StoryResponseDTO create(StoryRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyTitleUnique(requestDTO.getTitle()); // verificando que el campo "title" sea unico.
		// creando "story".
		StoryEntity storyEntity = this.storyMapper.mapRequestToEntity(requestDTO);
		CountryEntity countryEntity = this.getCountryById(requestDTO.getCountryId());
		DemographyEntity demographyEntity = this.getDemographyById(requestDTO.getDemographyId());
		CategoryEntity categoryEntity = this.getCategoryById(requestDTO.getCategoryId());
		Set<GenreEntity> genres = this.getListGenresbYIds(requestDTO.getGenreIds());
		Set<StoryMangakaEntity> storyMangakaEntities = this.convertToListStoriesMangakasEntities(requestDTO.getMangakaJobIds(), storyEntity);
		storyEntity.setCountry(countryEntity);
		storyEntity.setDemography(demographyEntity);
		storyEntity.setCategory(categoryEntity);
		storyEntity.setGenres(genres);
		storyEntity.setStoriesMangakas(storyMangakaEntities);
		StoryEntity storyCreated = this.storyRepository.save(storyEntity);
		// seteando "response".
		return this.getFullResponse(storyCreated, storyMangakaEntities);
	}

	@Override
	public StoryResponseDTO update(Integer id, StoryRequestDTO requestDTO) {	
		// TODO Auto-generated method stub
		this.storyMangakaRepository.deleteByStoryId(id); // eliminando relaciones anterioes con "mangakas".
		StoryEntity storyCurrent = this.getStoryById(id);
		this.verifyTitleUnique(requestDTO.getTitle(), storyCurrent.getTitle()); // verificando que el campo "title" sea unico.
		// actualizando "story".
		storyCurrent.setTitle(requestDTO.getTitle());
		storyCurrent.setYear(requestDTO.getYear());
		storyCurrent.setSynopsis(requestDTO.getSynopsis());
		storyCurrent.setState(requestDTO.getState());
		storyCurrent.setUrlImage(requestDTO.getUrlImage());
		storyCurrent.setAdaptationAnime(requestDTO.getAdaptationAnime());
		storyCurrent.setPrice(requestDTO.getPrice());
		CountryEntity countryEntity = this.getCountryById(requestDTO.getCountryId());
		DemographyEntity demographyEntity = this.getDemographyById(requestDTO.getDemographyId());
		CategoryEntity categoryEntity = this.getCategoryById(requestDTO.getCategoryId());
		Set<GenreEntity> genres = this.getListGenresbYIds(requestDTO.getGenreIds());
		Set<StoryMangakaEntity> storyMangakaEntities = this.convertToListStoriesMangakasEntities(requestDTO.getMangakaJobIds(), storyCurrent);
		storyCurrent.setCountry(countryEntity);
		storyCurrent.setDemography(demographyEntity);
		storyCurrent.setCategory(categoryEntity);
		storyCurrent.setGenres(genres);
		storyCurrent.setStoriesMangakas(storyMangakaEntities);	
		StoryEntity storyUpdated = this.storyRepository.save(storyCurrent);	
		// seteando "response. 
		return this.getFullResponse(storyUpdated, storyMangakaEntities);
	}
	
	@Override
	public StoryResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		this.storyMangakaRepository.deleteByStoryId(id); // eliminando relaciones con "stories_mangakas"
		StoryEntity entity = this.getStoryById(id);
		this.storyRepository.delete(entity);
		// seteando "response. 
		return this.storyMapper.mapEntityToResponseDTO(entity);
	}

	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public CountryEntity getCountryById(Integer id) {
		return this.countryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pais", "id", id));
	}
	
	
	public DemographyEntity getDemographyById(Integer id) {
		return this.demographyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Demografía", "id", id));
	}
	
	
	public CategoryEntity getCategoryById(Integer id) {
		return this.categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id));
	}
	
	public MangakaEntity getMangakaById(Integer id) {
		return this.mangakaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mangaka", "id", id));
	}
	
	public JobEntity getJobById(Integer id) {
		return this.jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ocupación", "id", id));
	}
	
	public StoryEntity getStoryById(Integer id) {
		return this.storyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Historieta", "id", id));
	}
	
	public Set<GenreEntity> getListGenresbYIds(Set<Integer> genresIds){
		Set<GenreEntity> genres = new HashSet<>();		
		for (Integer id : genresIds) {
			GenreEntity genreEntity = this.genreRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));
			genres.add(genreEntity);
		}
		return genres;
	} 
	
	public void verifyTitleUnique(String title) {
		Boolean existTitle = this.storyRepository.existsByTitle(title);
		if(existTitle) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El título " + title + " ya existe.");
		}
	}
	
	public void verifyTitleUnique(String title, String titleCurrent) {
		Boolean existTitle = this.storyRepository.existsByTitle(title);
		Boolean diferentTitleCurrent = (!title.equalsIgnoreCase(titleCurrent));
		if(existTitle && diferentTitleCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El título " + title + " ya existe.");
		}
	}

	public Set<MangakasJobsResponseDTO> convertToListMangakasJobsResponseDTO(Set<StoryMangakaEntity> storiesMangakas){
		return storiesMangakas.stream().map((x) -> {
			return MangakasJobsResponseDTO.builder()
					.mangakaId(x.getMangaka().getId())
					.nameMangaka(x.getMangaka().getName())
					.jobId(x.getJob().getId())
					.nameJob(x.getJob().getName())
					.build();
		}).collect(Collectors.toSet());	
	}
	
	public Set<StoryMangakaEntity> convertToListStoriesMangakasEntities(Set<MangakaJobRequestDTO> mangakaJobRequestDTOs, StoryEntity story){
		return mangakaJobRequestDTOs.stream().map((x) -> {
			MangakaEntity  mangaka = this.getMangakaById(x.getMangakaId());
			JobEntity job = this.getJobById(x.getJobId());
			return StoryMangakaEntity.builder()
				.story(story)
				.mangaka(mangaka)
				.job(job)
				.build();
		}).collect(Collectors.toSet());	
	}
	
	public StoryResponseDTO getFullResponse(StoryEntity story, Set<StoryMangakaEntity> storyMangakaEntities) {
		StoryResponseDTO response = this.storyMapper.mapEntityToResponseDTO(story);
		response.setMangakasJobs(this.convertToListMangakasJobsResponseDTO(storyMangakaEntities));
		return response;
	}

}