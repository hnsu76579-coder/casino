package com.casino.slotsystem.service;

import com.casino.slotsystem.dto.*;
import com.casino.slotsystem.entity.Slot;
import com.casino.slotsystem.entity.SlotHistory;
import com.casino.slotsystem.repository.SlotHistoryRepository;
import com.casino.slotsystem.repository.SlotRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final SlotHistoryRepository slotHistoryRepository;

    public SlotService(SlotRepository slotRepository,
                       SimpMessagingTemplate messagingTemplate,SlotHistoryRepository slotHistoryRepository) {
        this.slotRepository = slotRepository;
        this.messagingTemplate = messagingTemplate;
        this.slotHistoryRepository=slotHistoryRepository;
    }


    // PUBLIC
    @Cacheable("slots")
    public List<SlotResponse> getAllSlots() {
       return slotRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ADMIN - edit slot details
    public SlotResponse editSlot(Long id, EditSlotRequest request) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setSlotName(request.getSlotName());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setActive(request.getActive());

        return mapToResponse(slotRepository.save(slot));
    }

    // ADMIN - update number
    @CacheEvict(
            value = { "slots", "slot", "slotHistory" },
            allEntries = true
    )
    public SlotResponse updateNumber(Long id, Integer number) {
        if (number < -1) {
            throw new IllegalArgumentException("Invalid number");
        }

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        // 1️⃣ Update slot
        slot.setNumber(number);
        Slot savedSlot = slotRepository.save(slot);

        // 2️⃣ Save history
        SlotHistory history = new SlotHistory();
        history.setSlot(savedSlot);
        history.setNumber(number);
        history.setChangedAt(LocalDateTime.now());

        slotHistoryRepository.save(history);

        // 3️⃣ Send socket update
        SlotResponse response = mapToResponse(savedSlot);
        messagingTemplate.convertAndSend("/topic/slots", response);

        return response;
    }
    @Cacheable(value = "slot", key = "#id")
    public SlotResponse getSlotById(Long id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        return mapToResponse(slot);
    }

    private SlotResponse mapToResponse(Slot slot) {
        return new SlotResponse(
                slot.getId(),
                slot.getSlotName(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getNumber(),
                slot.getActive()
        );
    }
}

