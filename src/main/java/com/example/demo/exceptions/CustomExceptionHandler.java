package com.example.demo.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ResponseBean;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public ResponseBean errorHandler(Exception exception) {
		return new ResponseBean(null, true, exception.getMessage());
	}
}
