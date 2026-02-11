package com.casino.slotsystem.controller;

import com.casino.slotsystem.dto.AdminLoginRequest;
import com.casino.slotsystem.dto.AdminLoginResponse;
import com.casino.slotsystem.service.AdminAuthService;
import com.casino.slotsystem.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminAuthService authService;

    public AdminAuthController(AdminAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<AdminLoginResponse> login(
            @RequestBody AdminLoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return new ApiResponse<>(true, "Login successful",
                new AdminLoginResponse(token));
    }
}
