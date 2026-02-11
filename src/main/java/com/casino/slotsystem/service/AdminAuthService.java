package com.casino.slotsystem.service;

import com.casino.slotsystem.entity.Admin;
import com.casino.slotsystem.repository.AdminRepository;
import com.casino.slotsystem.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminAuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminAuthService(AdminRepository adminRepository,
                            PasswordEncoder passwordEncoder,
                            JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        if (admin.getLockUntil() != null &&
                admin.getLockUntil().isAfter(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Account locked. Try again after "
                            + admin.getLockUntil()
            );
        }
        if (!passwordEncoder.matches(password, admin.getPassword())) {

            int attempts = admin.getFailedAttempts() + 1;
            admin.setFailedAttempts(attempts);

            // Lock after 5 failures
            if (attempts >= 30) {
                admin.setLockUntil(LocalDateTime.now().plusMinutes(15));
                admin.setFailedAttempts(0); // reset
            }

            adminRepository.save(admin);
            throw new RuntimeException("Invalid credentials");
        }
        admin.setFailedAttempts(0);
        admin.setLockUntil(null);
        adminRepository.save(admin);
        return jwtUtil.generateToken(username);
    }
}
