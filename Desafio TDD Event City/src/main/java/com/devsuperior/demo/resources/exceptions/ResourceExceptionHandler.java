package com.devsuperior.demo.resources.exceptions;

import com.devsuperior.demo.services.exception.DataBaseException;
import com.devsuperior.demo.services.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler{


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = "Resource not found";
        HttpStatus status= HttpStatus.NOT_FOUND;
        StandardError errer=new StandardError(Instant.now(),status.value(),error, e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(errer);
    }
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request){
        String error = "DataBase error";
        HttpStatus status= HttpStatus.BAD_REQUEST;
        StandardError errer=new StandardError(Instant.now(),status.value(),error, e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(errer);
    }


}
