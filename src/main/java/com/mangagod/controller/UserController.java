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

import com.mangagod.dto.request.UserCreateRequestDTO;
import com.mangagod.dto.request.UserUpdateRequestDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.dto.response.UserResponseDTO;
import com.mangagod.dto.response.page.UsersPageResponseDTO;
import com.mangagod.service.UserService;
import com.mangagod.util.AppConstants;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
public class UserController {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private UserService userService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de listar a todos los usuarios.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping
	public ResponseEntity<MainResponse> getAllUsers(@RequestParam(value = "numberPage", defaultValue = AppConstants.NUM_PAGE_DEFAULT, required = false) int numberPage,
      											    @RequestParam(value = "sizePage", defaultValue = AppConstants.SIZE_PAGE_DEFAULT, required = false) int sizePage,
      											    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
      											    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_DEFAULT, required = false) String sortDir){
		UsersPageResponseDTO userAllPageableDataDTO = this.userService.getAll(numberPage, sizePage, sortBy, sortDir);
		MainResponse mainResponse = new MainResponse(true, "Lista de usuarios.", userAllPageableDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de obtener los datos de un usuario en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/{user_id}")
	public ResponseEntity<MainResponse> getUserById(@PathVariable (name = "user_id") int userId){
		UserResponseDTO dataDTO = this.userService.getById(userId);
		MainResponse mainResponse = new MainResponse(true, "Obteniendo usuario por ID.", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de crear un nuevo usuario.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PostMapping
	public ResponseEntity<MainResponse> createUser(@Valid @RequestBody UserCreateRequestDTO requestDTO){
		UserResponseDTO dataDTO = this.userService.create(requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El usuario ha sido creado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);	
	}
	
	@ApiOperation("Esta operación se encarga de actualizar los datos de un usuario.")
	@PreAuthorize("hasRole('ADMIN')") 
	@PutMapping("/{user_id}")
	public ResponseEntity<MainResponse> updateUser(@PathVariable (name = "user_id") int userId, 
			                                       @Valid @RequestBody UserUpdateRequestDTO requestDTO ){
		UserResponseDTO dataDTO = this.userService.update(userId, requestDTO); 
		MainResponse mainResponse = new MainResponse(true, "El usuario ha sido actualizado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);	
	}

	@ApiOperation("Esta operación se encarga de eliminar un usuario en base a su ID.")
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/{user_id}")
	public ResponseEntity<MainResponse> deleteUser(@PathVariable (name = "user_id") int userId){
		UserResponseDTO dataDTO = this.userService.delete(userId);
		MainResponse mainResponse = new MainResponse(true, "El usuario ha sido eliminado exitosamente!", dataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);	
	}
	
}