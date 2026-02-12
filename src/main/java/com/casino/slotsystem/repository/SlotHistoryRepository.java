package com.casino.slotsystem.repository;

import com.casino.slotsystem.entity.SlotHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotHistoryRepository extends JpaRepository<SlotHistory, Long> {

    List<SlotHistory> findBySlotIdAndChangedAtAfterOrderByChangedAtDesc(
        Long slotId,
        LocalDateTime date
);
}



