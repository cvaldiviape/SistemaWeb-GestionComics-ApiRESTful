package com.mangagod.service;

import com.mangagod.dto.request.GenreRequestDTO;
import com.mangagod.dto.response.GenreResponseDTO;
import com.mangagod.dto.response.page.GenresPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface GenreService extends BaseService<GenresPageResponseDTO, GenreResponseDTO, GenreRequestDTO, Integer>{

}