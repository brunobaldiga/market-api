package dev.bruno.marketapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "Market API",
                version = "1.0",
                description = "API for managing products, orders, and payments."
        )
)
public class MarketApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketApiApplication.class, args);
    }

}
