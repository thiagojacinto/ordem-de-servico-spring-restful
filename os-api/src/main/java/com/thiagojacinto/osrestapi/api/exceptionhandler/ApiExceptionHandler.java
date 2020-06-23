package com.thiagojacinto.osrestapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.thiagojacinto.osrestapi.api.exceptionhandler.ProblemDescription.ProblemType;
import com.thiagojacinto.osrestapi.domain.exception.DomainException;
import com.thiagojacinto.osrestapi.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomainException(DomainException exception, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var problema = new ProblemDescription();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(exception.getMessage());
		
		return handleExceptionInternal(exception, problema, new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleDomainException(EntityNotFoundException exception, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		
		var problema = new ProblemDescription();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(exception.getMessage());
		
		return handleExceptionInternal(exception, problema, new HttpHeaders(), status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var types = new ArrayList<ProblemDescription.ProblemType>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
//			String message = error.getDefaultMessage();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			types.add(new ProblemType(name, message));
		}
		
		var problem = new ProblemDescription();
		problem.setTypes(types);
		problem.setStatus(status.value());
		problem.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.");
		problem.setDataHora(OffsetDateTime.now());
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
}
