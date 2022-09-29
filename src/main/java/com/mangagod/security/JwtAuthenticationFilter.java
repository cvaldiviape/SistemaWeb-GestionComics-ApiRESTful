package com.mangagod.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override // con este metodo validamos el TOKEN
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// obtenemos el token de la solicitud HTTP
		String token = this.getJwtOfRequest(request);
		
		// validamos el token
		if(StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)) {
			
			//obtenemos el username del token
			String username = this.jwtTokenProvider.getUsernameOfToken(token);
			
			//cargamos el usuario asociado al token
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// establecemos la seguridad
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		// validamos el filtro
		filterChain.doFilter(request, response);
	}

	// “Bearer” -> Un formato que nos permite la autorización en conjunto con la autenticación de usuarios.
	// Bearer token de acceso
	private String getJwtOfRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			// EJM: token: "Bearer sljk4kljklj4kjlkjl2kjnb4mnmm3bm444jk3"
			// enconres recortamos el token, es decirm que este sin la palabra "Bearer" al inicio, solo queremos el conjunto de caracteres
			return bearerToken.substring(7, bearerToken.length());
			// https://www.youtube.com/watch?v=lA7DORzdp98
			// https://www.programiz.com/java-programming/online-compiler/
		}
		
		return null;
	}

}