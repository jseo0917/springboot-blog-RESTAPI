package com.springboot.blog.exceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.Dto.ErrDetails;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrDetails> handleReourceNotFoundException(ResourceNotFoundException exception,
            WebRequest webRequest) {
            ErrDetails errorDetails = new ErrDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrDetails> handleBlogAPIException(BlogAPIException exception,
            WebRequest webRequest) {
            ErrDetails errorDetails = new ErrDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    // Global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrDetails> handleGlobalException(Exception exception,
            WebRequest webRequest) {
            ErrDetails errorDetails = new ErrDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String ErrMsg = error.getDefaultMessage();
            errors.put(fieldName, ErrMsg);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);        
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrDetails> handleAccessDeniedException(AccessDeniedException exception,
            WebRequest webRequest) {
            ErrDetails errorDetails = new ErrDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
