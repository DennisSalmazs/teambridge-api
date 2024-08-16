package com.teambridge.entity;

import com.teambridge.service.KeycloakService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BaseEntityListener {

    private final KeycloakService keycloakService;

    public BaseEntityListener(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PrePersist
    private void onPrePersist(BaseEntity entity) {
        String username = keycloakService.getLoggedInUserName();
        if (username != null && !username.equals("anonymous")) {
            entity.setInsertDateTime(LocalDateTime.now());
            entity.setLastUpdateDateTime(LocalDateTime.now());
            entity.setInsertUserUsername(username);
            entity.setLastUpdateUserUsername(username);
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity entity) {
        String username = keycloakService.getLoggedInUserName();
        if (username != null && !username.equals("anonymous")) {
            entity.setLastUpdateDateTime(LocalDateTime.now());
            entity.setLastUpdateUserUsername(username);
        }
    }

}
