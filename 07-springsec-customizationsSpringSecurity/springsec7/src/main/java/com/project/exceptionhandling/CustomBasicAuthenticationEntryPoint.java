package com.project.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		LocalDateTime currentTimeStamp=LocalDateTime.now();
		String message=(authException!=null && authException.getMessage()!=null)?authException.getMessage():"Unauthorized";
		String path=request.getRequestURI();
		
		// CUSTOMISING HEADER
		response.setHeader("easybank-error-reason", "Authentication failed");
		//response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		// CUSTOMISING BODY
		response.setContentType("application/json;charset=UTF-8");
		
		// Construct JSON Response
		String jsonResponse = 
				String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                        currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        message, path);
		response.getWriter().write(jsonResponse);
	}
}
