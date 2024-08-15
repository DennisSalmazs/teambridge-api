package com.teambridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(httpRequests -> httpRequests
//                        .anyRequest().permitAll()
                        .requestMatchers("/api/v1/user/**").hasAuthority("Admin")
                        .requestMatchers("/api/v1/project/**").hasAuthority("Manager")
                        .requestMatchers("/api/v1/task/**").hasAuthority("Manager")
                        .requestMatchers("/api/v1/task/employee/**").hasAuthority("Employee")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {

                    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

                    Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim("resource_access");

                    resourceAccess.forEach((resource, resourceClaims) -> {

                        if (resource.equals("teambridge-app")) {

                            Collection<String> roles = resourceClaims.get("roles");

                            roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
                        }
                    });

                    return new JwtAuthenticationToken(jwt, grantedAuthorities);

                })))
                .build();

    }
}
