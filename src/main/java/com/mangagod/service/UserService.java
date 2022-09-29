package com.mangagod.service;

import com.mangagod.dto.request.UserCreateRequestDTO;
import com.mangagod.dto.request.UserUpdateRequestDTO;
import com.mangagod.dto.response.UserResponseDTO;
import com.mangagod.dto.response.page.UsersPageResponseDTO;
import com.mangagod.service.base.BaseUserService;

public interface UserService extends BaseUserService<UsersPageResponseDTO, UserResponseDTO, UserCreateRequestDTO, UserUpdateRequestDTO, Integer>{
	
	
}