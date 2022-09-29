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

import com.mangagod.dto.request.TypeCharacterRequestDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.TypeCharacterResponseDTO;
import com.mangagod.dto.response.page.TypeCharactersPageResponseDTO;
import com.mangagod.service.TypeCharacterService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("type-character")
public class TypeCharacterController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private TypeCharacterService typeCharacterService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los tipos de personajes.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllTypeCharacters(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											         @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											         @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											         @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		TypeCharactersPageResponseDTO pageableDataDTO = this.typeCharacterService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de tipos de personsajes.", pageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un pais en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{type_character_id}")
	public ResponseEntity<MainResponse> getTypeCharacterById(@PathVariable (name = "type_character_id") int typeCharacterId){
		TypeCharacterResponseDTO dataDTO = this.typeCharacterService.getById(typeCharacterId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo tipo de personaje por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear un nuevo tipo de personaje.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createTypeCharacter(@Valid @RequestBody TypeCharacterRequestDTO requestDTO){
		TypeCharacterResponseDTO dataDTO = this.typeCharacterService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El tipo de personaje ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de un tipo de personaje.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{type_character_id}")
	public ResponseEntity<MainResponse> updateTypeCharacter(@PathVariable (name = "type_character_id") int typeCharacterId, 
													        @Valid @RequestBody TypeCharacterRequestDTO requestDTO ){
		TypeCharacterResponseDTO dataDTO = this.typeCharacterService.update(typeCharacterId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El pais ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar un pais en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{type_character_id}")
	public ResponseEntity<MainResponse> deleteTypeCharacter(@PathVariable (name = "type_character_id") int typeCharacterId){
		TypeCharacterResponseDTO dataDTO = this.typeCharacterService.delete(typeCharacterId);
		MainResponse mainResponse = new MainResponse(true, "El tipo de personaje ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}		

}