package com.cricket.exception;

import com.cricket.dto.response.ContentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ContentResponse<Object>> handleIllegalArgument(
      IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(
        new ContentResponse<>(
            "error",
            null,
            "FAILURE",
            "400",
            ex.getMessage()
        )
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ContentResponse<Object>> handleAll(Exception e) {
    e.printStackTrace();
    ContentResponse<Object> errorResponse = new ContentResponse<>(
        "error",
        null,
        "FAILURE",
        "500",
        "Something went wrong"
    );
    return ResponseEntity.internalServerError().body(errorResponse);
  }



  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ContentResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        new ContentResponse<>(
            "error",
            null,
            "FAILURE",
            "403",
            "You are not authorized to perform this action"
        )
    );
  }


}
