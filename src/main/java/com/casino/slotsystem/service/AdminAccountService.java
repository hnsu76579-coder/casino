package com.casino.slotsystem.service;

import com.casino.slotsystem.dto.ChangePasswordRequest;
import com.casino.slotsystem.dto.ChangeUsernameRequest;
import com.casino.slotsystem.entity.Admin;
import com.casino.slotsystem.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountService(AdminRepository adminRepository,
                               PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔐 CHANGE PASSWORD
    public void changePassword(String username, ChangePasswordRequest request) {

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // 1️⃣ Old password check
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // 2️⃣ New + confirm match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        // 3️⃣ Save new password
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);
    }

    // 👤 CHANGE USERNAME
    public void changeUsername(String currentUsername, ChangeUsernameRequest request) {

        Admin admin = adminRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // 1️⃣ Password verification
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        // 2️⃣ Username uniqueness
        if (adminRepository.findByUsername(request.getNewUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 3️⃣ Save new username
        admin.setUsername(request.getNewUsername());
        adminRepository.save(admin);
    }
}
