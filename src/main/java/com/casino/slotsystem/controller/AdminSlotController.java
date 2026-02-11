package com.casino.slotsystem.controller;

import com.casino.slotsystem.dto.*;
import com.casino.slotsystem.service.SlotService;
import com.casino.slotsystem.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/slots")

public class AdminSlotController {

    private final SlotService slotService;

    public AdminSlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    // Edit slot details
    @PutMapping("/{id}")
    public ApiResponse<SlotResponse> editSlot(
            @PathVariable Long id,
            @RequestBody EditSlotRequest request) {

        return new ApiResponse<>(
                true,
                "Slot updated",
                slotService.editSlot(id, request)
        );
    }

    // Change number (-1 allowed)
    @PutMapping("/{id}/number")
    public ApiResponse<SlotResponse> updateNumber(
            @PathVariable Long id,
            @RequestBody UpdateNumberRequest request) {

        return new ApiResponse<>(
                true,
                "Number updated",
                slotService.updateNumber(id, request.getNumber())
        );
    }
}
