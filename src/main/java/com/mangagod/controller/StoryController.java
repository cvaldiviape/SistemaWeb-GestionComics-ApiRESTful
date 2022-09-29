package com.mangagod.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mangagod.dto.request.StoryRequestDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.StoryResponseDTO;
import com.mangagod.dto.response.page.StoriesPageResponseDTO;
import com.mangagod.service.StoryService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("story")
public class StoryController {
	
	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private StoryService storyService;
		
	// NOTA: terminar controlador story
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todas las historietas.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllStories(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											  @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											  @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											  @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		StoriesPageResponseDTO storyAllPageableDataDTO = this.storyService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de historietas.", storyAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de una historieta en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{story_id}")
	public ResponseEntity<MainResponse> getStoryById(@PathVariable (name = "story_id") int storyId){
		StoryResponseDTO dataDTO = this.storyService.getById(storyId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo historieta por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear un nueva historieta.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createStory(@Valid @RequestBody StoryRequestDTO requestDTO){
		StoryResponseDTO dataDTO = this.storyService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La historieta ha sido creada exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de una historieta.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{story_id}")
	public ResponseEntity<MainResponse> updateStory(@PathVariable (name = "story_id") int storyId, 
												    @Valid @RequestBody StoryRequestDTO requestDTO ){
		StoryResponseDTO dataDTO = this.storyService.update(storyId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La historieta ha sido actualizada exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar una historieta en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{story_id}")
	public ResponseEntity<MainResponse> deleteStory(@PathVariable (name = "story_id") int storyId){
		StoryResponseDTO dataDTO = this.storyService.delete(storyId);
		MainResponse mainResponse = new MainResponse(true, "La historieta ha sido eliminada exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

}