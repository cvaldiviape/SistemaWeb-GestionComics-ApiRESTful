package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.GenreEntity;
import com.mangagod.repository.base.BaseRepository;

public interface GenreRepository extends BaseRepository<GenreEntity, Integer>{

	public Optional<GenreEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}