package com.zaychik.web.match.dto;

import lombok.Value;

@Value
public class PlayerPositionDto {
    Long userId;
    String username;
    Integer cellNum;
}