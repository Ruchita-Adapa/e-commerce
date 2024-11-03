package com.app.e_commerce.service;

import com.app.e_commerce.dto.request.LoginUserRequest;
import com.app.e_commerce.dto.response.UserDto;
import com.app.e_commerce.models.UserModel;
import com.app.e_commerce.repository.UserRepository;
import com.app.e_commerce.dto.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    public boolean createUser(CreateUserRequest createUserRequest) {
        UUID uuid = UUID.randomUUID();
        UserModel userData = UserModel.builder()
                .address(createUserRequest.getAddress())
                .email(createUserRequest.getEmail())
                .name(createUserRequest.getName())
                .id(uuid.toString())
                .password(createUserRequest.getPassword())
                .build();
        return userRepository.createUser(userData);
    }

    public UserDto getUserById(String userId) {
        UserModel userData = userRepository.getUserById(userId);
        return UserDto.builder()
                .name(userData.getName())
                .email(userData.getEmail())
                .address(userData.getAddress()).build();
    }

    public List<UserDto> getAllUsers(int offset, int limit) {
        List<UserModel> userData = userRepository.getAllUsers(offset, limit);
        return userData.stream()
                .map(user -> UserDto.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean updateUser(CreateUserRequest createUserRequest,String userId) {
        UserModel userData = UserModel.builder()
                .address(createUserRequest.getAddress())
                .email(createUserRequest.getEmail())
                .name(createUserRequest.getName())
                .build();
        return userRepository.updateUser(userData, userId);
    }

    public boolean deleteUser(String userId) {
        return userRepository.deleteUser(userId);
    }

    public String login(LoginUserRequest loginUserRequest) {
        UserModel userInfo = userRepository.getUserInfoByEmail(loginUserRequest);
        if(userInfo != null) {
            HashMap<String, Object> userInformation = new HashMap<>();
            userInformation.put("userId", userInfo.getId());
            return jwtService.generateToken(userInformation, userInfo.getName());
        } else {
            //TODO create a custom exception to handle user not found
            throw new RuntimeException("User not found");
        }
    }
}
