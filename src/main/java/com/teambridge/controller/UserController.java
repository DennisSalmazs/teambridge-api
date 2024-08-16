package com.teambridge.controller;

import com.teambridge.dto.ResponseWrapper;
import com.teambridge.dto.UserDTO;
import com.teambridge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users are successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Users could not be retrieved",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseWrapper> getUsers() {
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity
                .ok(new ResponseWrapper("Users are successfully retrieved", userDTOList));
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get User By Username")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable String username) {
        UserDTO userDTO = userService.findByUserName(username);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully retrieved", userDTO));
    }

    @PostMapping()
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User is successfully created",201));
    }

    @PutMapping("/{username}")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable String username, @RequestBody UserDTO user) {
        userService.update(username,user);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated"));
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete User")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully deleted"));
    }

}
