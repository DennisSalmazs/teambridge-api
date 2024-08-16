package com.teambridge.service;

import com.teambridge.dto.UserDTO;

public interface KeycloakService {

    String getLoggedInUserName();

    void userCreate(UserDTO user);

    void userUpdate(UserDTO user);

    void userDelete(String username);
}
