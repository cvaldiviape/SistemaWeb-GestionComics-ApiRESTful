package com.mangagod.service;

import com.mangagod.dto.request.AuthRequestDTO;
import com.mangagod.dto.request.TokenRequestDTO;
import com.mangagod.dto.response.AuthResponseDTO;

public interface AuthService {
	
	public AuthResponseDTO login(AuthRequestDTO authRequestDTO);
	
	public String refreshToken(TokenRequestDTO tokenRequestDTO);

}