package com.enviro.assessment.grad001.amosmaganyane.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Waste Management API")
                        .description("API for managing waste categories, recycling tips," +
                                " and disposal guidelines")
                        .version("1.0"));
    }
}