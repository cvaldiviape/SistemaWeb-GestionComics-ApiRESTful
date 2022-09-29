package com.mangagod.service;

import com.mangagod.dto.request.DemographyRequestDTO;
import com.mangagod.dto.response.DemographyResponseDTO;
import com.mangagod.dto.response.page.DemographiesPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface DemographyService extends BaseService<DemographiesPageResponseDTO, DemographyResponseDTO, DemographyRequestDTO, Integer>{
	
}