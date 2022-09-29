package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.CharacterEntity;
import com.mangagod.repository.base.BaseRepository;

public interface CharacterRepository extends BaseRepository<CharacterEntity,Integer> {

	public Optional<CharacterEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}