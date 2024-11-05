package com.example.m450.lb1.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Flugbuchungs API", version = "${spring.application.version}"))
public class OpenApi30Config {

}