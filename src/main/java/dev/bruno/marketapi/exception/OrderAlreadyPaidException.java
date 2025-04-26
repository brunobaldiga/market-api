package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OrderAlreadyPaidException extends MarketException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        pb.setTitle("Order already paid");
        pb.setDetail("This order has already been paid and cannot receive additional payments");

        return pb;
    }
}
