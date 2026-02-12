package com.casino.slotsystem.service;

import com.casino.slotsystem.dto.SlotHistoryResponse;
import com.casino.slotsystem.repository.SlotHistoryRepository;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;


@Service
public class SlotHistoryService {

    private final SlotHistoryRepository repository;

    public SlotHistoryService(SlotHistoryRepository repository) {
        this.repository = repository;
    }
   

    @Cacheable(
        value = "slotHistory",
        key = "#slotId"
)
public List<SlotHistoryResponse> getSlotHistory(Long slotId) {

    LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

    return repository
            .findBySlotIdAndChangedAtAfterOrderByChangedAtDesc(
                    slotId,
                    oneMonthAgo
            )
            .stream()
            .map(h -> new SlotHistoryResponse(
                    h.getNumber(),
                    h.getChangedAt()
            ))
            .toList();
}
}




