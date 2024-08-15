package com.teambridge.controller;

import com.teambridge.dto.ResponseWrapper;
import com.teambridge.dto.UserDTO;
import com.teambridge.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User is successfully created",201));
    }

    @PutMapping("/{username}")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable String username, @RequestBody UserDTO user) {
        userService.update(username,user);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated"));
    }



}
