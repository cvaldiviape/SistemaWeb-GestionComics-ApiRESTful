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

import com.mangagod.dto.request.CharacterRequestDTO;
import com.mangagod.dto.response.CharacterResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.page.CharactersPageResponseDTO;
import com.mangagod.service.CharacterService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("character")
public class CharacterController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CharacterService characterService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los personajes.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllCharacters(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											     @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		CharactersPageResponseDTO characterAllPageableDataDTO = this.characterService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de personajes.", characterAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un personaje en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{character_id}")
	public ResponseEntity<MainResponse> getCharacterById(@PathVariable (name = "character_id") int characterId){
		CharacterResponseDTO dataDTO = this.characterService.getById(characterId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo personaje por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear un nuevo personaje.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createCharacter(@Valid @RequestBody CharacterRequestDTO requestDTO){
		CharacterResponseDTO dataDTO = this.characterService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El personaje ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de un personaje.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{character_id}")
	public ResponseEntity<MainResponse> updateCountry(@PathVariable (name = "character_id") int characterId, 
													  @Valid @RequestBody CharacterRequestDTO requestDTO ){
		CharacterResponseDTO dataDTO = this.characterService.update(characterId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El personaje ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar un personaje en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{character_id}")
	public ResponseEntity<MainResponse> deleteCountry(@PathVariable (name = "character_id") int characterId){
		CharacterResponseDTO dataDTO = this.characterService.delete(characterId);
		MainResponse mainResponse = new MainResponse(true, "El personaje ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}