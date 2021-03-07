package br.com.commons.exception;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = -4311044393182592686L;

	private String status;
	private String message;

	public ApplicationException() {
	}

	public ApplicationException(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
