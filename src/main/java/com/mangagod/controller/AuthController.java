package com.mangagod.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangagod.dto.request.AuthRequestDTO;
import com.mangagod.dto.request.TokenRequestDTO;
import com.mangagod.dto.response.AuthResponseDTO;
import com.mangagod.dto.response.MainResponse;
import com.mangagod.service.AuthService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("auth")
public class AuthController {
	
	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private AuthService authService;
	
	// ---------------------------------------------------------- controllers ----------------------------------------------------------- //
	@ApiOperation("Esta operación se encarga de la autenticación del usuario.")
	@PostMapping("/login")
	public ResponseEntity<MainResponse> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
		AuthResponseDTO authDataDTO = this.authService.login(authRequestDTO);
		MainResponse mainResponse = new MainResponse(true, "El usuario se ha autenticado exitosamente!", authDataDTO);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
	@ApiOperation("Esta operación se encarga de verificar que el token se encuentre vigente.")
	@PostMapping("/refresh-token")
	public ResponseEntity<MainResponse> refreshToken(@Valid @RequestBody TokenRequestDTO tokenRequestDTO){
		String tokenRefreshed = this.authService.refreshToken(tokenRequestDTO);
		MainResponse mainResponse = new MainResponse(true, "Token refrescado exitosamente!", tokenRefreshed);
		return new ResponseEntity<MainResponse>(mainResponse, HttpStatus.OK);
	}
	
}