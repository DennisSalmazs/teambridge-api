package com.teambridge.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KeycloakProperties {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${master.user.username}")
    private String masterUser;

    @Value("${master.user.password}")
    private String masterUserPswd;

    @Value("${master.realm}")
    private String masterRealm;

    @Value("${master.client}")
    private String masterClient;


}
