package com.mangagod.repository;

import java.util.Optional;
import com.mangagod.entity.RoleEntity;
import com.mangagod.repository.base.BaseRepository;

public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {

	public Optional<RoleEntity> findByName(String name);
	
	public Boolean existsByName(String name);
	
}