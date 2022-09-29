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

import com.mangagod.dto.request.JobRequestDTO;
import com.mangagod.dto.response.JobResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.page.JobsPageResponseDTO;
import com.mangagod.service.JobService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("job")
public class JobController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private JobService jobService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todas las ocupaciones.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllJobs(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											 @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											 @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											 @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		JobsPageResponseDTO jobAllPageableDataDTO = this.jobService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de ocupaciones.", jobAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de una ocupación en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{job_id}")
	public ResponseEntity<MainResponse> getJobById(@PathVariable (name = "job_id") int jobId){
		JobResponseDTO dataDTO = this.jobService.getById(jobId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo ocupación por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear una nueva ocupación.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createJob(@Valid @RequestBody JobRequestDTO requestDTO){
		JobResponseDTO dataDTO = this.jobService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La ocupación ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de una ocupación.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{job_id}")
	public ResponseEntity<MainResponse> updateJob(@PathVariable (name = "job_id") int jobId, 
			                                      @Valid @RequestBody JobRequestDTO requestDTO ){
		JobResponseDTO dataDTO = this.jobService.update(jobId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La ocupación ha sido actualizada exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar una ocupación en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{job_id}")
	public ResponseEntity<MainResponse> deleteJob(@PathVariable (name = "job_id") int jobId){
		JobResponseDTO dataDTO = this.jobService.delete(jobId);
		MainResponse mainResponse = new MainResponse(true, "La ocupación ha sido eliminada exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}