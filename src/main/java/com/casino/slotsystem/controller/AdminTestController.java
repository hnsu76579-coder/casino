package com.casino.slotsystem.controller;

import com.casino.slotsystem.util.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminTestController {

    @GetMapping("/secure-test")
    public ApiResponse<String> secureTest(Authentication authentication) {

        String username = authentication.getName();

        return new ApiResponse<>(
                true,
                "JWT is working",
                "Hello " + username
        );
    }
}
