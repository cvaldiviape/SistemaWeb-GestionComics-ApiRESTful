package com.mangagod.service.base;

public interface BaseService <PAGEABLE, DataDTO, RequestDTO, ID> {

	public PAGEABLE getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir);
	
	public DataDTO getById(ID id);
	
	public DataDTO create(RequestDTO requestDTO);
	
	public DataDTO update(ID id, RequestDTO requestDTO);
	
	public DataDTO delete(ID id);

}