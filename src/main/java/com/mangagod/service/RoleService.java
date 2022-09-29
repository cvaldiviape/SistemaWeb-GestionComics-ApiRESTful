package com.mangagod.service;

import com.mangagod.dto.request.RoleRequestDTO;
import com.mangagod.dto.response.RoleResponseDTO;
import com.mangagod.dto.response.page.RolesPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface RoleService extends BaseService<RolesPageResponseDTO, RoleResponseDTO, RoleRequestDTO, Integer>{
	
}