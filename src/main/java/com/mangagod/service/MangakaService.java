package com.mangagod.service;

import com.mangagod.dto.request.MangakaRequestDTO;
import com.mangagod.dto.response.MangakaResponseDTO;
import com.mangagod.dto.response.page.MangakasPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface MangakaService extends BaseService<MangakasPageResponseDTO, MangakaResponseDTO, MangakaRequestDTO, Integer>{

}