package com.mangagod.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import com.mangagod.entity.UserEntity;
import com.mangagod.repository.base.BaseRepository;

public interface UserRepository extends BaseRepository<UserEntity, Integer> {

	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByUsernameOrEmail(String username, String email);
	
	public Optional<UserEntity> findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	@Query("SELECT u FROM UserEntity u "
	     + "JOIN u.roles r "
	     + "WHERE r.id = ?1 AND (u.username = ?2 OR u.email = ?2)")
	public Optional<UserEntity> findByUsernameOrEmailAndRoleId(Integer roleId, String userNameOrEmail);
	
}