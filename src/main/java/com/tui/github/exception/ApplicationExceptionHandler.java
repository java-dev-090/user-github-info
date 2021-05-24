package com.tui.github.exception;

import com.tui.github.utils.ExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ExceptionMessage> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        log.error("Error from WebClient - Status {}, Body {}", HttpStatus.NOT_ACCEPTABLE.name(),
                ex.getMessage(), ex);
        ExceptionMessage exceptionMessage = new ExceptionMessage(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .contentType(MediaType.APPLICATION_JSON).body(exceptionMessage);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionMessage> handleWebClientResponseException(WebClientResponseException ex) {
        log.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(),
                ex.getResponseBodyAsString(), ex);
        ExceptionMessage message = new ExceptionMessage(
                ex.getStatusCode().value(),
                HttpStatus.NOT_FOUND.value()== ex.getStatusCode().value()?
                        ExceptionConstant.USER_NOT_FOUND_EXCEPTION:ex.getMessage());
        return new ResponseEntity<>(message, ex.getStatusCode());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionMessage> handleInternalServerException(Exception ex) {
        log.error("Error from Exception - Status {}, Body {}", HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage(), ex);
        ExceptionMessage er = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
