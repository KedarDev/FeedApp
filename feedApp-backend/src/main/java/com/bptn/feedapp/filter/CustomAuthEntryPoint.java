package com.bptn.feedapp.filter;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component // spring managed component 
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

	@Autowired // instance
	@Qualifier("handlerExceptionResolver")
	HandlerExceptionResolver resolver;
	
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
	    	
	    	this.resolver.resolveException(req, res, null, ex);
	}
    
}