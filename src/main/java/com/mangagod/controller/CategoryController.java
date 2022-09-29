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

import com.mangagod.dto.request.CategoryRequestDTO;
import com.mangagod.dto.response.CategoryResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.page.CategoriesPageResponseDTO;
import com.mangagod.service.CategoryService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("category")
public class CategoryController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private CategoryService categoryService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos las categorias.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllCategories(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
	      											     @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
	      											     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
	      											     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		CategoriesPageResponseDTO countryAllPageableDataDTO = this.categoryService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de categorias.", countryAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de una categorias en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{category_id}")
	public ResponseEntity<MainResponse> getCategoryById(@PathVariable (name = "category_id") int categoryId){
		CategoryResponseDTO dataDTO = this.categoryService.getById(categoryId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo categoria por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear una nueva categoria.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createCategory(@Valid @RequestBody CategoryRequestDTO requestDTO){
		CategoryResponseDTO dataDTO = this.categoryService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La categoria ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de una categoria.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{category_id}")
	public ResponseEntity<MainResponse> updateCountry(@PathVariable (name = "category_id") int categoryId, 
													  @Valid @RequestBody CategoryRequestDTO requestDTO ){
		CategoryResponseDTO dataDTO = this.categoryService.update(categoryId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "La categoria ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar una categoria en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{category_id}")
	public ResponseEntity<MainResponse> deleteCountry(@PathVariable (name = "category_id") int categoryId){
		CategoryResponseDTO dataDTO = this.categoryService.delete(categoryId);
		MainResponse mainResponse = new MainResponse(true, "La categoria ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}