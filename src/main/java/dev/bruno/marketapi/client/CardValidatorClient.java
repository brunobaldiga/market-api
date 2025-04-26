package dev.bruno.marketapi.client;

import dev.bruno.marketapi.client.dto.ValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "card-validator",
        url = "${client.card-validator-service.url}"
)
public interface CardValidatorClient {
    @GetMapping
    ResponseEntity<ValidationResponse> isValid();
}
