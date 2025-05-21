package dev.bruno.marketapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EntityNotFoundException extends MarketException {
    private Long entityId;
    private String entityName;

    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundException(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Entity not found");

        if (entityId != null) {
            pb.setDetail("Entity with id " + entityId + " not found");
        } else {
            pb.setDetail("Entity with name " + entityName + " not found");
        }

        return pb;
    }
}
