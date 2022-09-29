package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.DemographyEntity;
import com.mangagod.repository.base.BaseRepository;

public interface DemographyRepository extends BaseRepository<DemographyEntity, Integer> {

	public Optional<DemographyEntity> findByName(String name);
	
	public Boolean existsByName(String name);

}