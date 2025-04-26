package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class DuplicateProductException extends MarketException {
    private String productName;

    public DuplicateProductException(String productName) {
        this.productName = productName;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        pb.setTitle("Duplicate product");
        pb.setDetail("Product with name " + productName + " already exists");

        return pb;
    }
}
