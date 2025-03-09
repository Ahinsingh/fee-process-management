package com.feeprocess.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Camunda Fee Process Service").version("v1.0.0")
                        .description("We will try to make fee process workflow using Springboot Camunda Workflow.")
                        .termsOfService("Terms of Service").license(getLicense()).contact(getContact()));

    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("ahinsingh@gmail.com");
        contact.setName("Ahin Singh");
        contact.setUrl("");
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }

    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0");
        license.setUrl("https://opensource.org/licenses/Apache-2.0");
        license.setExtensions(Collections.emptyMap());
        return license;
    }

}