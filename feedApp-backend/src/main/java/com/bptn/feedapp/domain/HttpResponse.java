package com.bptn.feedapp.domain;

import java.util.Date;
import org.springframework.http.HttpStatus;
import java.text.SimpleDateFormat;

public class HttpResponse {

	Date timeStamp; //  object that stores the date and time
	int httpStatusCode; // 200, 201, 400, 500
	HttpStatus httpStatus; // stores HTTP status response such as OK, CREATED, BAD_REQUEST, or INTERNAL_SERVER_ERROR
	String reason; // stores the reason for the HTTP response
	String message; // stores the message for the HTTP response.
	
	// Contractor 
	 public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
	        this.timeStamp = new Date();
	        this.httpStatusCode = httpStatusCode;
	        this.httpStatus = httpStatus;
	        this.reason = reason;
	        this.message = message;
	}

	// Getters & Setters
	public Date getTimeStamp() {
		return timeStamp;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getReason() {
		return reason;
	}

	public String getMessage() {
		return message;
	}
	
	// toString()
	@Override
	public String toString() {
			
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.timeStamp);
			return "HttpResponse [timeStamp=" + timeStamp + ", httpStatusCode=" + httpStatusCode + ", httpStatus="
					+ httpStatus + ", reason=" + reason + ", message=" + message + "]";
	}
	
}
