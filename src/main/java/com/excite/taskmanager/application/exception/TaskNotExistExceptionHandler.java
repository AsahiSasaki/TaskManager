package com.excite.taskmanager.application.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.exception.ValidationException;

@RestControllerAdvice
public class TaskNotExistExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotExistException.class)
    public ResponseEntity<Object> handleTaskNotExistException(TaskNotExistException e, @NonNull WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(e.getMessage(), path);
        HttpHeaders httpHeaders = new HttpHeaders();
        return handleExceptionInternal(e, apiError, httpHeaders, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExistException(ValidationException e, @NonNull WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError apiError = new ApiError(e.getMessage(), path);
        HttpHeaders httpHeaders = new HttpHeaders();
        return handleExceptionInternal(e, apiError, httpHeaders, HttpStatus.BAD_REQUEST, request);
    }
}
