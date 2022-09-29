package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.TypeCharacterEntity;
import com.mangagod.repository.base.BaseRepository;

public interface TypeCharacterRepository extends BaseRepository<TypeCharacterEntity, Integer> {

	public Optional<TypeCharacterEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}