package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidCardException extends MarketException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pb.setTitle("Invalid credit/debit card");
        pb.setDetail("Payment failed due to invalid card details");

        return pb;
    }
}
