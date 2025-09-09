package com.zaychik.web.action.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class GameEvent {
    private EventType eventType;
    private Long userId; 
    private Integer cellNum;

    public enum EventType {
        MOVE,
        TAKE_CELL
    }
}