package com.mangagod.security;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mangagod.dto.request.AuthRequestDTO;
import com.mangagod.exception.MangaGodAppException;
import com.mangagod.util.AppSettingProperties;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component // "JwtTokenProvider" este clase se va encargar de generar el TOKEN, obtener los CLAIMS, validar el TOKEN.
public class JwtTokenProvider {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AppSettingProperties appSettingProperties;

	public String generateToken(Authentication authentication) {
		
		String username = authentication.getName();
		Date fechaActual = new Date();
		Date fechaExpiracion = new Date(fechaActual.getTime() + this.appSettingProperties.JWT_EXPIRATION_IN_MLS);
		
		String token = Jwts.builder()
							.setSubject(username)
							.setIssuedAt(new Date())
							.setExpiration(fechaExpiracion)
							.signWith(SignatureAlgorithm.HS512, this.appSettingProperties.JWT_SECRET)
							.compact();	
		return token;
	}
	
	public String getUsernameOfToken(String token) {
		// Claims: es un objeto que contiene los datos del token, EJM: user, rol, fecha de caducidad.
						// asignando la clave secreta la "clave secreta" que ira acompañada del "token"
		Claims claims = Jwts.parser().setSigningKey(this.appSettingProperties.JWT_SECRET).parseClaimsJws(token).getBody();
		
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.appSettingProperties.JWT_SECRET).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"Firma JWT no valida.");
		}
		catch (MalformedJwtException ex) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"Token JWT no valida.");
		}
		catch (ExpiredJwtException ex) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"Token JWT caducado.");
		}
		catch (UnsupportedJwtException ex) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible.");
		}
		catch (IllegalArgumentException ex) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT esta vacia.");
		}
	}
	
	public Optional<String> getToken(AuthRequestDTO authRequestDTO) {
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDTO.getUsernameOrEmail(), authRequestDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return Optional.ofNullable(this.generateToken(authentication));
	}
	
	public Optional<String> refreshToken(String token) {
		String tokenRefresed = null;
		try {
			JWT jwt = JWTParser.parse(token);
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
	        String username = claims.getSubject();
	        Date fechaActual = new Date();
			Date fechaExpiracion = new Date(fechaActual.getTime() + this.appSettingProperties.JWT_EXPIRATION_IN_MLS);
	      
	        tokenRefresed = Jwts.builder()
				                .setSubject(username)
				                .setIssuedAt(new Date())
				                .setExpiration(fechaExpiracion)
								.signWith(SignatureAlgorithm.HS512, this.appSettingProperties.JWT_SECRET)
								.compact();
		} catch (ParseException e) {
			throw new MangaGodAppException(HttpStatus.BAD_REQUEST,"Token no valido.");
		}
		return Optional.ofNullable(tokenRefresed);
       
	}
	
}