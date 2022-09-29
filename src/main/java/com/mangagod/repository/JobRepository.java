package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.JobEntity;
import com.mangagod.repository.base.BaseRepository;

public interface JobRepository extends BaseRepository<JobEntity, Integer> {

	public Optional<JobEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}