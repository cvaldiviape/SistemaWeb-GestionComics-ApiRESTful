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

import com.mangagod.dto.request.DemographyRequestDTO;
import com.mangagod.dto.response.DemographyResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.page.DemographiesPageResponseDTO;
import com.mangagod.service.DemographyService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("demography")
public class DemographyController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private DemographyService demographyService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los datos demográficos.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllDemographies(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											       @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											       @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											       @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		DemographiesPageResponseDTO demographyAllPageableDataDTO = this.demographyService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de datos demograficos.", demographyAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un dato demografico en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{demography_id}")
	public ResponseEntity<MainResponse> getDemographyById(@PathVariable (name = "demography_id") int demographyId){
		DemographyResponseDTO dataDTO = this.demographyService.getById(demographyId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo un dato demográfico por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de crear un dato demografico.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createDemography(@Valid @RequestBody DemographyRequestDTO requestDTO){
		DemographyResponseDTO dataDTO = this.demographyService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El dato demográfico ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de actualizar los datos de un dato demográfico.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{demography_id}")
	public ResponseEntity<MainResponse> updateDemography(@PathVariable (name = "demography_id") int demographyId, 
													     @Valid @RequestBody DemographyRequestDTO requestDTO ){
		DemographyResponseDTO dataDTO = this.demographyService.update(demographyId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El dato demográfico ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de eliminar un dato demográfico en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{demography_id}")
	public ResponseEntity<MainResponse> deleteDemography(@PathVariable (name = "demography_id") int demographyId){
		DemographyResponseDTO dataDTO = this.demographyService.delete(demographyId);
		MainResponse mainResponse = new MainResponse(true, "El dato demográfico ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}