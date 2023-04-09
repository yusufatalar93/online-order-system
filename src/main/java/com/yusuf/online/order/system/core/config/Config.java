package com.yusuf.online.order.system.core.config;

import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class Config {


  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }


  @RestControllerAdvice
  public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> handleValidationErrors(
        MethodArgumentNotValidException ex) {
      final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      Map<String, String> errorMap = new HashMap<>();
      for (FieldError fieldError : fieldErrors) {
        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return new ResponseEntity<>(getErrorsMap(errorMap), new HttpHeaders(),
          HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(
        RuntimeException ex) {
      final String  message = ex.getMessage();
      return new ResponseEntity<>(message, new HttpHeaders(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Map<String, String>> getErrorsMap(Map<String, String> errors) {
      Map<String, Map<String, String>> errorResponse = new HashMap<>();
      errorResponse.put("errors", errors);
      return errorResponse;
    }

  }
}
