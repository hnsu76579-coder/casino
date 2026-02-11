package com.casino.slotsystem.dto;

import java.time.LocalTime;

public class SlotResponse {

    private Long id;
    private String slotName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer number;
    private Boolean active;

    public SlotResponse() {}

    public SlotResponse(Long id, String slotName,
                        LocalTime startTime, LocalTime endTime,
                        Integer number, Boolean active) {
        this.id = id;
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = number;
        this.active = active;
    }

    // getters & setters

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
