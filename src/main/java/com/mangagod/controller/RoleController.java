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

import com.mangagod.dto.request.RoleRequestDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.RoleResponseDTO;
import com.mangagod.dto.response.page.RolesPageResponseDTO;
import com.mangagod.service.RoleService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("role")
public class RoleController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private RoleService roleService;
	 
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los roles.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllRoles(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
      											    @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
      											    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
      											    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		RolesPageResponseDTO roleAllPageableDataDTO = this.roleService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de roles.", roleAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un rol en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{role_id}")
	public ResponseEntity<MainResponse> getRoleId(@PathVariable (name = "role_id") int roleId){
		RoleResponseDTO roleDataDTO = this.roleService.getById(roleId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo rol por ID.", roleDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de crear un nuevo rol.")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<MainResponse> createRole(@Valid @RequestBody RoleRequestDTO requestDTO){
		RoleResponseDTO dataDTO = this.roleService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El rol ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de actualizar los datos de un rol.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{role_id}")
	public ResponseEntity<MainResponse> updateRol(@PathVariable (name = "role_id") int roleId, 
			                                      @Valid @RequestBody RoleRequestDTO requestDTO ){
		RoleResponseDTO dataDTO = this.roleService.update(roleId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El rol ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}

	@ApiOperation("Esta operación se encarga de eliminar un rol en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{role_id}")
	public ResponseEntity<MainResponse> deleteRole(@PathVariable (name = "role_id") int roleId){
		RoleResponseDTO dataDTO = this.roleService.delete(roleId);
		MainResponse mainResponse = new MainResponse(true, "El rol ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}