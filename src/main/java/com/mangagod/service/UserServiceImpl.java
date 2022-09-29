package com.mangagod.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mangagod.dto.request.UserCreateRequestDTO;
import com.mangagod.dto.request.UserUpdateRequestDTO;
import com.mangagod.dto.response.UserResponseDTO;
import com.mangagod.dto.response.page.UsersPageResponseDTO;
import com.mangagod.dto.response.view.UserViewResponseDTO;
import com.mangagod.entity.RoleEntity;
import com.mangagod.entity.UserEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.UserMapper;
import com.mangagod.repository.RoleRepository;
import com.mangagod.repository.UserRepository;
import com.mangagod.util.AppHelpers;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	// ----------------------------------------------------- dependency injection  ----------------------------------------------------- //
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AppHelpers appHelpers;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public UsersPageResponseDTO getAll(Integer numberPage, Integer sizePage, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Pageable pageable = this.appHelpers.getPageable(numberPage, sizePage, sortBy, sortDir);
		Page<UserEntity> usersPageable = this.userRepository.findAll(pageable);
		List<UserEntity> usersEntity = usersPageable.getContent();
		List<UserViewResponseDTO> usersDto = usersEntity.stream().map((x) -> this.userMapper.mapEntityToViewResponseDTO(x)).collect(Collectors.toList());	
		
		return UsersPageResponseDTO.builder()
				.users(usersDto)
				.numberPage(usersPageable.getNumber())
				.sizePage(usersPageable.getSize())
				.totalElements(usersPageable.getTotalElements())
				.totalPages(usersPageable.getTotalPages())
				.isLastPage(usersPageable.isLast())
				.build();
	}
	
	@Override
	public UserResponseDTO getById(Integer id) {
		// TODO Auto-generated method stub
		UserEntity entity = this.getUserById(id);
		UserResponseDTO dataDTO = this.userMapper.mapEntityToResponseDTO(entity);
		return dataDTO;
	}

	@Override
	public UserResponseDTO create(UserCreateRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		this.verifyUserNameUnique(requestDTO.getUsername());
		this.verifyEmailUnique(requestDTO.getEmail());
		
		UserEntity entity = this.userMapper.mapRequestToEntity(requestDTO);
		entity.setPassword(this.passwordEncoder.encode(requestDTO.getPassword())); // encriptando la contraseña

		Set<RoleEntity> roles = new HashSet<>();		
		for (Integer roleId : requestDTO.getRoleIds()) {
			RoleEntity roleEntity = this.getRoleById(roleId);
			roles.add(roleEntity);
		}
		entity.setRoles(roles);
		
		UserResponseDTO dataCreated = this.userMapper.mapEntityToResponseDTO(this.userRepository.save(entity));		
		return dataCreated;
	}

	@Override
	public UserResponseDTO update(Integer id, UserUpdateRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		UserEntity dataCurrent = this.getUserById(id);
		
		this.verifyUserNameUnique(requestDTO.getUsername(), dataCurrent.getUsername());
		this.verifyEmailUnique(requestDTO.getEmail(), dataCurrent.getEmail());
		
		dataCurrent.setUsername(requestDTO.getUsername());
		dataCurrent.setEmail(requestDTO.getEmail());
		
		Set<RoleEntity> roles = new HashSet<>();
		for (Integer roleId : requestDTO.getRoleIds()) {
			RoleEntity roleEntity = this.roleRepository.findById(roleId).orElseThrow(() -> new MangaGodAppException(HttpStatus.BAD_REQUEST, "El rol con Id " + roleId + " no existe."));
			roles.add(roleEntity);
		}
		dataCurrent.setRoles(roles);
		
		UserResponseDTO dataUpdated = this.userMapper.mapEntityToResponseDTO(this.userRepository.save(dataCurrent));	
		return dataUpdated;
	}

	@Override
	public UserResponseDTO delete(Integer id) {
		// TODO Auto-generated method stub
		UserEntity entity = this.getUserById(id);
		this.userRepository.delete(entity);
		UserResponseDTO dataDeleted = this.userMapper.mapEntityToResponseDTO(entity);
		return dataDeleted;
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public UserEntity getUserById(Integer id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
	}
	
	public RoleEntity getRoleById(Integer id) {
		return this.roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Rol", "id", id));
	}
	
	public void verifyUserNameUnique(String username) {
		Boolean existUsername = this.userRepository.existsByUsername(username);
		if(existUsername) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El username " + username + " ya existe.");
		}
	}
	
	public void verifyUserNameUnique(String username, String usernameCurrent) {
		Boolean existUsername = this.userRepository.existsByUsername(username);
		Boolean diferentUsernameCurrent = (!username.equalsIgnoreCase(usernameCurrent));
		if(existUsername && diferentUsernameCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El username " + username + " ya existe.");
		}
	}
	
	public void verifyEmailUnique(String email) {
		Boolean existEmail = this.userRepository.existsByUsername(email);
		if(existEmail) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El email " + email + " ya existe.");
		}
	}
	
	public void verifyEmailUnique(String email, String emailCurrent) {
		Boolean existEmail = this.userRepository.existsByUsername(email);
		Boolean diferentEmailCurrent = (!email.equalsIgnoreCase(emailCurrent));
		if(existEmail && diferentEmailCurrent) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST, "El email " + email + " ya existe.");
		}
	}
	
}