package com.casino.slotsystem.repository;

import com.casino.slotsystem.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}
