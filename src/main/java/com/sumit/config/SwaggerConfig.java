package com.sumit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket personApi(){



        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.paths(PathSelectors.ant("/api/person/*").or(PathSelectors.ant("/api/photo/*")))
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.sumit.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API Title",
                "REST API Description.",
                "Version : 1.0.0",
                "Terms of services",
                new Contact("Sumit Mittal", "https://www.linkedin.com/in/tech-sumit-mittal/", "sumitmittal.adonis@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}