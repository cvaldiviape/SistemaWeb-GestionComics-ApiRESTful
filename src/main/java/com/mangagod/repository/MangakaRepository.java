package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.MangakaEntity;
import com.mangagod.repository.base.BaseRepository;

public interface MangakaRepository extends BaseRepository<MangakaEntity, Integer>{

	public Optional<MangakaEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}