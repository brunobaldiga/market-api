package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EntityNotFoundException extends MarketException {
    private Long entityId;

    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Entity not found");
        pb.setDetail("Entity with id " + entityId + " not found");

        return pb;
    }
}
