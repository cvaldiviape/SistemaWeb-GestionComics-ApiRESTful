package com.mangagod.exception;

import org.springframework.http.HttpStatus;

public class MangaGodAppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private HttpStatus state;
	private String message;
	
	public MangaGodAppException(HttpStatus state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	
	public MangaGodAppException(HttpStatus state, String message, String messageOne) {
		super();
		this.state = state;
		this.message = message;
		this.message = messageOne;
	}

	public HttpStatus getState() {
		return state;
	}

	public void setState(HttpStatus state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
