package com.mangagod.service.base;

public interface BaseUserService  <PAGEABLE, DataDTO, CreateRequestDTO, UpdateRequestDTO, ID> {

	public PAGEABLE getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir);
	
	public DataDTO getById(ID id);
	
	public DataDTO create(CreateRequestDTO createRequestDTO);
	
	public DataDTO update(ID id, UpdateRequestDTO updateRequestDTO);
	
	public DataDTO delete(ID id);

}