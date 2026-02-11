package com.casino.slotsystem.controller;

import com.casino.slotsystem.dto.ChangePasswordRequest;
import com.casino.slotsystem.dto.ChangeUsernameRequest;
import com.casino.slotsystem.service.AdminAccountService;
import com.casino.slotsystem.util.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/account")
public class AdminAccountController {

    private final AdminAccountService accountService;

    public AdminAccountController(AdminAccountService accountService) {
        this.accountService = accountService;
    }

    // 🔐 Change password
    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request) {

        String username = authentication.getName();
        accountService.changePassword(username, request);

        return new ApiResponse<>(true, "Password changed successfully", null);
    }

    // 👤 Change username
    @PutMapping("/change-username")
    public ApiResponse<Void> changeUsername(
            Authentication authentication,
            @RequestBody ChangeUsernameRequest request) {

        String username = authentication.getName();
        accountService.changeUsername(username, request);

        return new ApiResponse<>(true, "Username changed successfully", null);
    }
}
