package com.example.demo.core.ex;


import com.example.demo.core.until.Resp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class GlovalApiExceptionHandler {

    // 400 Bad Request 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 첫 번째 발생한 필드 오류 메시지를 반환
        FieldError fieldError = ex.getBindingResult().getFieldError();

        String reason = fieldError != null ? fieldError.getDefaultMessage() : "실제사유";

        return new ResponseEntity<>(Resp.fail(400, reason), HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NoHandlerFoundException e) {
        return new ResponseEntity<>(Resp.fail(404, "실제사유"), HttpStatus.NOT_FOUND);
    }
}
