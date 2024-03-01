package com.aston.orderservice.controller;

import com.aston.orderservice.entity.ErrorBody;
import com.aston.orderservice.exception.NotValidStatusException;
import com.aston.orderservice.exception.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler({NotValidStatusException.class})
    public ResponseEntity<ErrorBody> handleNotValidStatusException(NotValidStatusException e){
        return ResponseEntity.status(400)
                .body(
                        ErrorBody.builder()
                                .statusCode(400)
                                .message("Bad request")
                                .details(e.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ErrorBody> handleOrderNotFoundException(OrderNotFoundException e){
        return ResponseEntity.status(400)
                .body(
                        ErrorBody.builder()
                                .statusCode(400)
                                .message("Bad request")
                                .details(e.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorBody> handleThrowable(Throwable e){
        return ResponseEntity.status(500)
                .body(ErrorBody.builder()
                        .statusCode(500)
                        .message("Internal Server Error")
                        .details(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
