package com.teambridge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TeamBridge App API Docs",
                description = "REST API Documentation for TeamBridge App",
                version = "v1",
                contact = @Contact(
                        name = "TeamBridge",
                        email = "teambridge@email.com",
                        url = "https://www.teambridge.com"
                )),
        security = {@SecurityRequirement(name = "Keycloak")}
)
@SecurityScheme(
        name = "Keycloak",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "http://localhost:8080/realms/teambridge-dev/protocol/openid-connect/auth",
                tokenUrl = "http://localhost:8080/realms/teambridge-dev/protocol/openid-connect/token")))
public class SwaggerConfig {


}
