package com.mangagod.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.mangagod.security.CustomUserDetailsService;
import com.mangagod.security.JwtAuthenticationEntryPoint;
import com.mangagod.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration // se encarga de registrar BEANs.
@EnableWebSecurity // se emplea para crear una clase de configuracion personalizada.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // "WebSecurityConfigurerAdapter" contiene funciones que hashean 
																          // las contraseñas, pero en este caso vamos a sobreescribirlas.
	
	@Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf()
			.disable() // desabilitamos por que spring ya cuenta con uno propio.
			.authorizeRequests()
			.antMatchers(
					//HttpMethod.POST,
					"/auth/**"
					//,"/user/**", "/role/**"
			)
			.permitAll()
			.antMatchers(
					"/v2/api-docs/**",
					"/swagger-ui/**",
					"/swagger-resources/**",
					"/configuration/**"		
			)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		http.addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailsService).passwordEncoder(this.passwordEncoder());
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
}