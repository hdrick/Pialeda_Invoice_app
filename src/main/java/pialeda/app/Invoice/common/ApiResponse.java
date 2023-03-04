package pialeda.app.Invoice.common;

import java.time.LocalDateTime;


import org.springframework.http.ResponseEntity;

public class ApiResponse {
	private final boolean success;
	private final String message;
	
	// try again
	
	public ApiResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}



	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
	
	public String getTimestamp() {
		return LocalDateTime.now().toString();
	}
}
