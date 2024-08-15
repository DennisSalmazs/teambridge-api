package com.teambridge.service.impl;

import com.teambridge.service.KeycloakService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Override
    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> attributes = ((JwtAuthenticationToken) authentication).getTokenAttributes();
        return (String) attributes.get("preferred_username");
    }
}
