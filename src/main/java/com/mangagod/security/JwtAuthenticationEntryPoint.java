package com.mangagod.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("delegatedAuthenticationEntryPoint") // esta clase "JwtAuthenticationEntryPoint" se encargara de enviar el mensaje de error en caso el usuario haga una peticion a la API sin su token
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sin autorización");
		resolver.resolveException(request, response, null, authException);
	}
	
}