package com.casino.slotsystem.controller;

import com.casino.slotsystem.dto.SlotResponse;
import com.casino.slotsystem.service.SlotService;
import com.casino.slotsystem.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping
    public ApiResponse<List<SlotResponse>> getAllSlots() {
        return new ApiResponse<>(
                true,
                "Slots fetched",
                slotService.getAllSlots()
        );
    }
    @GetMapping("/{id}")
    public ApiResponse<SlotResponse> getSlotById(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Slot fetched",
                slotService.getSlotById(id)
        );
    }
}
