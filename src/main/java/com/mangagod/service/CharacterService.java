package com.mangagod.service;

import com.mangagod.dto.request.CharacterRequestDTO;
import com.mangagod.dto.response.CharacterResponseDTO;
import com.mangagod.dto.response.page.CharactersPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface CharacterService extends BaseService<CharactersPageResponseDTO, CharacterResponseDTO, CharacterRequestDTO, Integer>{

}