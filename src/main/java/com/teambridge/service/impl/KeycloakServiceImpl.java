package com.teambridge.service.impl;

import com.teambridge.config.KeycloakProperties;
import com.teambridge.dto.UserDTO;
import com.teambridge.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;


@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakProperties keycloakProperties;

    public KeycloakServiceImpl(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> attributes = ((JwtAuthenticationToken) authentication).getTokenAttributes();
        return (String) attributes.get("preferred_username");
    }

    @Override
    public void userCreate(UserDTO userDTO) {

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(userDTO.getPassWord());

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(userDTO.getUserName());
        keycloakUser.setFirstName(userDTO.getFirstName());
        keycloakUser.setLastName(userDTO.getLastName());
        keycloakUser.setEmail(userDTO.getUserName());
        keycloakUser.setCredentials(List.of(credential));
        keycloakUser.setEmailVerified(true);
        keycloakUser.setEnabled(true);

        try (Keycloak keycloak = getKeycloakInstance()) {

            RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());

            UsersResource usersResource = realmResource.users();

            // Create Keycloak user
            Response result = usersResource.create(keycloakUser);

            String userId = getCreatedId(result);

            ClientRepresentation appClient = realmResource.clients()
                    .findByClientId(keycloakProperties.getClientId()).get(0);

            RoleRepresentation userClientRole = realmResource.clients().get(appClient.getId())
                    .roles().get(userDTO.getRole().getDescription()).toRepresentation();

            realmResource.users().get(userId).roles().clientLevel(appClient.getId())
                    .add(List.of(userClientRole));

        }

    }


    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakProperties.getAuthServerUrl(),
                keycloakProperties.getMasterRealm(),
                keycloakProperties.getMasterUser(),
                keycloakProperties.getMasterUserPswd(),
                keycloakProperties.getMasterClient());
    }
}
