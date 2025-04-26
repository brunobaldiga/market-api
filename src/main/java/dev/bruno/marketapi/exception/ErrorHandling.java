package dev.bruno.marketapi.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(MarketException.class)
    public ResponseEntity<ProblemDetail> handleMarketException(MarketException ex) {
        return ResponseEntity
                .status(ex.toProblemDetail().getStatus())
                .body(ex.toProblemDetail());
    }
}