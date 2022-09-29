package com.mangagod.service;

import com.mangagod.dto.request.CategoryRequestDTO;
import com.mangagod.dto.response.CategoryResponseDTO;
import com.mangagod.dto.response.page.CategoriesPageResponseDTO;
import com.mangagod.service.base.BaseService;

public interface CategoryService extends BaseService<CategoriesPageResponseDTO, CategoryResponseDTO, CategoryRequestDTO, Integer>{

}