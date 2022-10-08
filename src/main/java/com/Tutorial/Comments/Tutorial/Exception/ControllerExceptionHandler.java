package com.Tutorial.Comments.Tutorial.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException notFoundException, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
          HttpStatus.NOT_FOUND.value(),
          new Date(),
          notFoundException.getMessage(),
          request.getDescription(false)
          );
        
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
    
   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          new Date(),
          exception.getMessage(),
          request.getDescription(false)
        );
        
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
