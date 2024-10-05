package com.vn.identity_service.controller;

import com.vn.identity_service.dto.request.ApiResponse;
import com.vn.identity_service.dto.request.UserCreationRequest;
import com.vn.identity_service.dto.request.UserUpdateRequest;
import com.vn.identity_service.dto.response.UserResponse;
import com.vn.identity_service.entity.User;
import com.vn.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ApiResponse<User> createUsser(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(userCreationRequest));
        return apiResponse;
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        log.info("Role: {}", authentication.getAuthorities());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder().result(userService.getUser(userId)).build();
    }

    @GetMapping("/users/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/users/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest user) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, user))
                .build();
    }

    @DeleteMapping("/users/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("Delete user successfully")
                .build();
    }
}
