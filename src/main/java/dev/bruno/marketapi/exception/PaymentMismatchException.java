package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PaymentMismatchException extends MarketException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pb.setTitle("Payment amount exceeds due total");
        pb.setDetail("Card payment amount exceeds the remaining amount due.");


        return pb;
    }
}
