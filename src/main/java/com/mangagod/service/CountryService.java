package com.mangagod.service;

import com.mangagod.dto.request.CountryRequestDTO;
import com.mangagod.dto.response.CountryResponseDTO;
import com.mangagod.dto.response.page.CountriesPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface CountryService extends BaseService<CountriesPageResponseDTO, CountryResponseDTO, CountryRequestDTO, Integer>{

}