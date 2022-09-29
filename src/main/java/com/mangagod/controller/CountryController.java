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

import com.mangagod.dto.request.CountryRequestDTO;
import com.mangagod.dto.response.CountryResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.page.CountriesPageResponseDTO;
import com.mangagod.service.CountryService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("country")
public class CountryController {
	
	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CountryService countryService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los paises.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllCountries(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											    @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		CountriesPageResponseDTO countryAllPageableDataDTO = this.countryService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de paises.", countryAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un pais en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{country_id}")
	public ResponseEntity<MainResponse> getCountryById(@PathVariable (name = "country_id") int countryId){
		CountryResponseDTO dataDTO = this.countryService.getById(countryId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo pais por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear un nuevo pais.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createCountry(@Valid @RequestBody CountryRequestDTO requestDTO){
		CountryResponseDTO dataDTO = this.countryService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El pais ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de un pais.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{country_id}")
	public ResponseEntity<MainResponse> updateCountry(@PathVariable (name = "country_id") int countryId, 
													  @Valid @RequestBody CountryRequestDTO requestDTO ){
		CountryResponseDTO dataDTO = this.countryService.update(countryId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El pais ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar un pais en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{country_id}")
	public ResponseEntity<MainResponse> deleteCountry(@PathVariable (name = "country_id") int countryId){
		CountryResponseDTO dataDTO = this.countryService.delete(countryId);
		MainResponse mainResponse = new MainResponse(true, "El pais ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}