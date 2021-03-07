package br.com.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4311044393182592686L;

	private String status_code;
	
	private String message;

	public ApplicationNotFoundException() {
	}

	public ApplicationNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
	public ApplicationNotFoundException(String status, String message) {
		super(message);
		this.status_code = status;
		this.message = message;
	}

	

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
