package com.teambridge.controller;

import com.teambridge.dto.ResponseWrapper;
import com.teambridge.dto.UserDTO;
import com.teambridge.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity
                .ok(new ResponseWrapper("Users are successfully retrieved", userDTOList));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable String username) {
        UserDTO userDTO = userService.findByUserName(username);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully retrieved", userDTO));
    }



}
