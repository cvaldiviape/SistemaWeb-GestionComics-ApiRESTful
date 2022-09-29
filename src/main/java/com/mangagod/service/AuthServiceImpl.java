package com.mangagod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.mangagod.dto.request.AuthRequestDTO;
import com.mangagod.dto.request.TokenRequestDTO;
import com.mangagod.dto.response.AuthResponseDTO;
import com.mangagod.dto.response.RoleResponseDTO;
import com.mangagod.dto.response.TokenResponseDTO;
import com.mangagod.dto.response.UserAuthResponseDTO;
import com.mangagod.entity.RoleEntity;
import com.mangagod.entity.UserEntity;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.exception.ResourceNotFoundException;
import com.mangagod.mapper.AuthMapper;
import com.mangagod.mapper.RoleMapper;
import com.mangagod.repository.RoleRepository;
import com.mangagod.repository.UserRepository;
import com.mangagod.security.JwtTokenProvider;
import com.mangagod.util.AppSettingProperties;

@Service
public class AuthServiceImpl implements AuthService {

	// ----------------------------------------------------- dependency injection ----------------------------------------------------- //
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthMapper authMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private AppSettingProperties appSettingProperties;
	
	// ----------------------------------------------------------- services ----------------------------------------------------------- //
	@Override
	public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
		// TODO Auto-generated method stub
		String usernameOrEmail = authRequestDTO.getUsernameOrEmail();
		Integer roleId = authRequestDTO.getRoleId();

		String token = this.auth(authRequestDTO);
		UserEntity userEntity = this.getUserIfExistWithRole(roleId, usernameOrEmail);
		RoleEntity roleEntity = this.getRoleById(roleId);
		
		UserAuthResponseDTO userAuthResponseDTO = this.authMapper.mapEntityToResponse(userEntity);
		RoleResponseDTO roleResponseDTO = this.roleMapper.mapEntityToResponseDTO(roleEntity);
		TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(token, this.appSettingProperties.JWT_TYPE, this.appSettingProperties.JWT_EXPIRATION_IN_MLS);
	
		return AuthResponseDTO.builder()
				.user(userAuthResponseDTO)
				.role(roleResponseDTO)
				.token(tokenResponseDTO)
				.build();			
	}

	@Override
	public String refreshToken(TokenRequestDTO tokenRequestDTO) {
		String tokenRefreshed = this.jwtTokenProvider.refreshToken(tokenRequestDTO.getTokenAccess())
				.orElseThrow(() -> new MangaGodAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Bad credentials"));
		return tokenRefreshed;
	}
	
	// ----------------------------------------------------------- utils ----------------------------------------------------------- //
	public RoleEntity getRoleById(Integer id) {
		return this.roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
	}
	
	public String auth(AuthRequestDTO authRequestDTO) {
		return this.jwtTokenProvider.getToken(authRequestDTO)
				.orElseThrow(() -> new MangaGodAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Bad credentials"));
	}
	
	public UserEntity getUserIfExistWithRole(Integer roleId, String usernameOrEmail) {
		return this.userRepository.findByUsernameOrEmailAndRoleId(roleId, usernameOrEmail)
				.orElseThrow(() -> new MangaGodAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Bad credentials"));
	}
	
}