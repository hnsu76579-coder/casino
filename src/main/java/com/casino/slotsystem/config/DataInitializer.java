package com.casino.slotsystem.config;

import com.casino.slotsystem.entity.Slot;
import com.casino.slotsystem.repository.SlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class    DataInitializer {

    @Bean
    CommandLineRunner initSlots(SlotRepository slotRepository) {
        return args -> {
            if (slotRepository.count() == 0) {
                for (int i = 1; i <= 8; i++) {
                    Slot slot = new Slot();
                    slot.setSlotName("Slot " + i);
                    slot.setStartTime(LocalTime.of(10 + i, 0));
                    slot.setEndTime(LocalTime.of(11 + i, 0));
                    slot.setNumber(0);
                    slotRepository.save(slot);
                }
            }
        };
    }
}
