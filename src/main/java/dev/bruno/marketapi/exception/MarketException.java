package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class MarketException extends RuntimeException {
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Market internal server error");

        return pb;
    }
}
