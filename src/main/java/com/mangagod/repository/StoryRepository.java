package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.StoryEntity;
import com.mangagod.repository.base.BaseRepository;

public interface StoryRepository extends BaseRepository<StoryEntity, Integer> {

	public Optional<StoryEntity> findByTitle(String name);
	
	public Boolean existsByTitle(String name);
	
}