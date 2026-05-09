package com.bootcamp.tasktracker.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Task Tracker API")
                        .version("1.0.0")
                        .description("API documentation for the Student Task Tracker application")
                        .contact(new Contact()
                                .name("Bootcamp Team")
                                .email("support@bootcamp.com")
                                .url("https://bootcamp.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
