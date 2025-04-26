package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientStockException extends MarketException {
    private String productName;

    public InsufficientStockException(String productName) {
        this.productName = productName;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        pb.setTitle("Insufficient Stock");
        pb.setDetail("Insufficient stock available for product: " + productName);

        return pb;
    }
}
