package com.mangagod.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mangagod.entity.RoleEntity;
import com.mangagod.entity.UserEntity;
import com.mangagod.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	// este metodo veririficara la existencia de un usuario en base a su "username" o "correo" para posteriormente retornar al usuario y 
	// sus roles en caso existiera. (este metodo lo uso en la clase "JwtAuthenticationFilter")
 	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
 	    UserEntity	user = this.userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
 				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el 'username' o 'email': " + usernameOrEmail));
	
		return new User(user.getEmail(), user.getPassword(), this.mapperRoles(user.getRoles()));
 	}
 	
 	private Collection<? extends GrantedAuthority> mapperRoles(Set<RoleEntity> roles){
 		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
 	}

}