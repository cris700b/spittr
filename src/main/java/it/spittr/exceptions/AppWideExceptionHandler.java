package it.spittr.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

	@ExceptionHandler(DuplicateSpittleException.class)
	public String duplicateSpittleHandler(){
		
		return "error/duplicate";
	}
	
	@ExceptionHandler(SpittleNotFoundException.class)
	public String spittleNotFoundHandler(){
		
		return "error/notFound";
	}
}
