package com.app.e_commerce.controller;

import com.app.e_commerce.dto.request.LoginUserRequest;
import com.app.e_commerce.dto.response.UserDto;
import com.app.e_commerce.models.UserModel;
import com.app.e_commerce.dto.request.CreateUserRequest;
import com.app.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public boolean createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") String userId) {
      return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "20") int limit) {
        return userService.getAllUsers(offset, limit);
    }

    @PutMapping("/users/{id}")
    public boolean updateUser(@RequestBody CreateUserRequest createUserRequest, @PathVariable("id") String userId) {
        return userService.updateUser(createUserRequest, userId);
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable("id") String userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody LoginUserRequest loginUserRequest) {
        return userService.login(loginUserRequest);
    }
}
