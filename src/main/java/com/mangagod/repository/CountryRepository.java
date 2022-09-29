package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.CountryEntity;
import com.mangagod.repository.base.BaseRepository;

public interface CountryRepository extends BaseRepository<CountryEntity,Integer> {

	public Optional<CountryEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}