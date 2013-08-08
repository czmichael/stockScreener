package com.ztech.exception;

public class WebServiceException extends RuntimeException {
	
	public WebServiceException(Exception e) {
		new RuntimeException(e.getMessage(), e.getCause());
	}
}
