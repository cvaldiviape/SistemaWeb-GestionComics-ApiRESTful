package com.mangagod.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mangagod.dto.response.ErrorDetailResponseDTO;
import com.mangagod.util.AppHelpers;

@ControllerAdvice // indico que esta clase manejara excepciones
public class GlobalHandlerException extends ResponseEntityExceptionHandler { // que herede "ResponseEntityExceptionHandler" para el manejo de excepciones en la validacion de campos
	
	@Autowired
	private AppHelpers appHelpers;
	
	// ---------------------------------------- CUSTOM EXCEPTIONS ---------------------------------------- //
	// Cada Exception que se lanze en la apliacion, previamente pasara por alguna de estas funciones
	// segun el tipo de exception, ello gracias que que definimos el annotation "@ControllerAdvice".
	
	@ExceptionHandler(ResourceNotFoundException.class) // indico que esta funcion recibira excepciones de tipo "ResourceNotFoundException"
	public ResponseEntity<ErrorDetailResponseDTO> managerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		String dateTime = this.appHelpers.convertLocalDateTimeToString(LocalDateTime.now());
		ErrorDetailResponseDTO errorDetailResponseDTO = new ErrorDetailResponseDTO(dateTime, exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetailResponseDTO>(errorDetailResponseDTO, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MangaGodAppException.class) // indico que esta funcion recibira excepciones de tipo "MangaGodAppException"
	public ResponseEntity<ErrorDetailResponseDTO> managerMangaGodAppException(MangaGodAppException exception, WebRequest webRequest){
		String dateTime = this.appHelpers.convertLocalDateTimeToString(LocalDateTime.now());
		ErrorDetailResponseDTO errorDetailResponseDTO = new ErrorDetailResponseDTO(dateTime, exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetailResponseDTO>(errorDetailResponseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class) // indico que esta funcion recibira excepciones de tipo "Exception"
	public ResponseEntity<ErrorDetailResponseDTO> managerException(Exception exception, WebRequest webRequest){
		String dateTime = this.appHelpers.convertLocalDateTimeToString(LocalDateTime.now());
		ErrorDetailResponseDTO errorDetailResponseDTO = new ErrorDetailResponseDTO(dateTime, exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetailResponseDTO>(errorDetailResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ErrorDetailResponseDTO> managerAuthenticationException(AuthenticationException exception, WebRequest webRequest) {
        String dateTime = this.appHelpers.convertLocalDateTimeToString(LocalDateTime.now());
		ErrorDetailResponseDTO errorDetailResponseDTO = new ErrorDetailResponseDTO(dateTime, exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetailResponseDTO>(errorDetailResponseDTO, HttpStatus.UNAUTHORIZED);
		// https://www.baeldung.com/spring-security-exceptionhandler
		// https://github.com/eugenp/tutorials/blob/master/spring-security-modules/spring-security-core-2/src/main/java/com/baeldung/global/exceptionhandler/handler/DefaultExceptionHandler.java
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, 
			                                                      HttpStatus status, WebRequest request) {
		// NOTA: "Map" es una interface que admite coleccion de tipo "clave" -> "valor"
		Map<String, String> errors = new HashMap<String, String>();
		
		// getBindingResult() -> me permite obtener todos los errores
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String nameField = ((FieldError)error).getField(); // nombre de campo en el cual se esta insertando un valor incorrecto
			String message = error.getDefaultMessage(); // es el mensaje que definimos en la validacion de campos en los DTO
			errors.put(nameField, message);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		// Metodo sobreescrito de la clase absracta ResponseEntityExceptionHandler
		// Se va encargar de controlar los errores de validacion que definimos en el DTO, es decir de los campos
	}

}