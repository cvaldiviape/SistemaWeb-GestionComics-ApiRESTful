package com.mangagod.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.MangakaRequestDTO;
import com.mangagod.dto.request.StoryJobRequestDTO;
import com.mangagod.dto.response.MangakaResponseDTO;
import com.mangagod.dto.response.StoriesJobsResponseDTO;
import com.mangagod.dto.response.page.MangakasPageResponseDTO;
import com.mangagod.dto.response.view.MangakaViewResponseDTO;
import com.mangagod.entity.JobEntity;
import com.mangagod.entity.MangakaEntity;
import com.mangagod.entity.StoryEntity;
import com.mangagod.entity.StoryMangakaEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.MangakaMapper;
import com.mangagod.repository.JobRepository;
import com.mangagod.repository.MangakaRepository;
import com.mangagod.repository.StoryMangakaRepository;
import com.mangagod.repository.StoryRepository;
import com.mangagod.util.AppHelpers;
import com.mangagod.util.enums.Sex;

@Service
@Transactional
public class MangakaServiceImpl implements MangakaService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private MangakaRepository mangakaRepository;
	@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private StoryMangakaRepository storyMangakaRepository;
	@Autowired
	private MangakaMapper mangakaMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public MangakasPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<MangakaEntity> mangakasPageable = this.mangakaRepository.findAll(pageable);	
		List<MangakaEntity> mangakasEntity = mangakasPageable.getContent();
		List<MangakaViewResponseDTO> mangakasDTO = mangakasEntity.stream().map((x) -> this.mangakaMapper.mapEntityToViewResponseDTO(x)).collect(Collectors.toList());		
		return MangakasPageResponseDTO.builder()
				.mangakas(mangakasDTO)
				.numberPage(mangakasPageable.getNumber())
				.sizePage(mangakasPageable.getSize())
				.totalElements(mangakasPageable.getTotalElements())
				.totalPages(mangakasPageable.getTotalPages())
				.isLastPage(mangakasPageable.isLast())
				.build();
	}

	@Override
	public MangakaResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		MangakaEntity entity = this.getMangakaById(id);
		// seteando "response".
		return this.getFullResponse(entity, entity.getStoriesMangakas());
	}

	@Override
	public MangakaResponseDTO create(MangakaRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyNameUnique(requestDTO.getName()); // verificando que el campo "name" sea unico.
		// creando "mangaka".
		MangakaEntity mangakaEntity = this.mangakaMapper.mapRequestToEntity(requestDTO);
		Set<StoryMangakaEntity> storyMangakaEntities = this.convertToListStoriesMangakasEntities(requestDTO.getStoryJobIds(), mangakaEntity);
		mangakaEntity.setStoriesMangakas(storyMangakaEntities);
		MangakaEntity mangakaCreated = this.mangakaRepository.save(mangakaEntity);
		// seteando "response".
		return this.getFullResponse(mangakaCreated, storyMangakaEntities);
	}

	@Override
	public MangakaResponseDTO update(Integer id, MangakaRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.storyMangakaRepository.deleteByMangakaId(id); // eliminando relaciones anterioes con "stories".
		MangakaEntity mangakaCurrent = this.getMangakaById(id);
		this.verifyNameUnique(requestDTO.getName(), mangakaCurrent.getName());  // verificando que el campo "name" sea unico.
		// actualizando "mangaka".
		mangakaCurrent.setName(requestDTO.getName());
		mangakaCurrent.setSex(Sex.valueOf(requestDTO.getSex()));
		mangakaCurrent.setBirthDate(requestDTO.getBirthDate());
		Set<StoryMangakaEntity> storyMangakaEntities = this.convertToListStoriesMangakasEntities(requestDTO.getStoryJobIds(), mangakaCurrent);
		mangakaCurrent.setStoriesMangakas(storyMangakaEntities);
		MangakaEntity mangakaUpdated = this.mangakaRepository.save(mangakaCurrent);
		// seteando "response. 
		return this.getFullResponse(mangakaUpdated, storyMangakaEntities);
	}

	@Override
	public MangakaResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		this.storyMangakaRepository.deleteByMangakaId(id); // eliminando relaciones con "stories_mangakas"
		MangakaEntity entity = this.getMangakaById(id);
		this.mangakaRepository.delete(entity);
		// seteando "response. 
		return this.mangakaMapper.mapEntityToResponseDTO(entity);	
	}

	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public StoryEntity getStoryById(Integer id) {
		return this.storyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Historieta", "id", id));
	}
	
	public JobEntity getJobById(Integer id) {
		return this.jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ocupación", "id", id));
	}
	
	public MangakaEntity getMangakaById(Integer id) {
		return this.mangakaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mangaka", "id", id));
	}
	
	public void verifyNameUnique(String name) {
		Boolean existNname = this.mangakaRepository.existsByName(name);
		if(existNname) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El nombre " + name + " ya existe.");
		}
	}
	
	public void verifyNameUnique(String name, String nameCurrent) {
		Boolean existNname = this.mangakaRepository.existsByName(name);
		Boolean diferentNameCurrent = (!name.equalsIgnoreCase(nameCurrent));
		if(existNname && diferentNameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El nombre " + name + " ya existe.");
		}
	}

	public Set<StoriesJobsResponseDTO> convertToListStoriesJobsResponseDTO(Set<StoryMangakaEntity> storiesMangakas){
		return storiesMangakas.stream().map((x) -> {
			return StoriesJobsResponseDTO.builder()
					.storyId(x.getStory().getId())
					.nameStory(x.getStory().getTitle())
					.jobId(x.getJob().getId())
					.nameJob(x.getJob().getName())
					.build();
		}).collect(Collectors.toSet());	
	}
	
	public Set<StoryMangakaEntity> convertToListStoriesMangakasEntities(Set<StoryJobRequestDTO> storyJobRequestDTOs, MangakaEntity mangaka){
		return storyJobRequestDTOs.stream().map((x) -> {
			StoryEntity  story = this.getStoryById(x.getStoryId());
			JobEntity job = this.getJobById(x.getJobId());
			return StoryMangakaEntity.builder()
				.mangaka(mangaka)
				.story(story)
				.job(job)
				.build();
		}).collect(Collectors.toSet());	
	}
	
	public MangakaResponseDTO getFullResponse(MangakaEntity mangaka, Set<StoryMangakaEntity> storyMangakaEntities) {
		MangakaResponseDTO response = this.mangakaMapper.mapEntityToResponseDTO(mangaka);
		response.setStoriesJobs(this.convertToListStoriesJobsResponseDTO(storyMangakaEntities));
		return response;
	}

}