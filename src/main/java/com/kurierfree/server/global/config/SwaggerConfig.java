package com.kurierfree.server.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "API 명세서",
                description = "kurier-free 프로젝트의 API 명세서",
                version = "v2"
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){

        // 서버

        // 로컬
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local server for testing");

        return new OpenAPI()
                // .addSecurityItem(securityRequirement)
                // .components(components)
                .servers(List.of(localServer));}

}

