package com.casino.slotsystem.dto;

import java.time.LocalDateTime;

public class SlotHistoryResponse {

    private Integer number;
    private LocalDateTime changedAt;

    public SlotHistoryResponse(Integer number, LocalDateTime changedAt) {
        this.number = number;
        this.changedAt = changedAt;
    }

    public Integer getNumber() {
        return number;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
