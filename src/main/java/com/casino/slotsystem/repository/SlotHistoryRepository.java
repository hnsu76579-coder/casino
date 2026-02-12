package com.casino.slotsystem.repository;

import com.casino.slotsystem.entity.SlotHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotHistoryRepository extends JpaRepository<SlotHistory, Long> {

    // Page<SlotHistory> findBySlotIdOrderByChangedAtDesc(
    //         Long slotId,
    //         Pageable pageable
    // );

    List<SlotHistory> findBySlotIdAndChangedAtAfterOrderByChangedAtDesc(
        Long slotId,
        LocalDateTime date
);
}

