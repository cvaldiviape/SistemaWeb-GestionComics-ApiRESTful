package com.mangagod.service;

import com.mangagod.dto.request.StoryRequestDTO;
import com.mangagod.dto.response.StoryResponseDTO;
import com.mangagod.dto.response.page.StoriesPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface StoryService extends BaseService<StoriesPageResponseDTO, StoryResponseDTO, StoryRequestDTO, Integer>{
	
}