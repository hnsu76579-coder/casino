package com.casino.slotsystem.controller;

import com.casino.slotsystem.dto.SlotHistoryResponse;
import com.casino.slotsystem.service.SlotHistoryService;
import com.casino.slotsystem.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotHistoryController {

    private final SlotHistoryService service;

    public SlotHistoryController(SlotHistoryService service) {
        this.service = service;
    }

    
    @GetMapping("/{id}/history")
public ApiResponse<List<SlotHistoryResponse>> getHistory(
        @PathVariable Long id
) {
    return new ApiResponse<>(
            true,
            "Slot history fetched",
            service.getSlotHistory(id)
    );
}
}


