package com.zaychik.web.action.dto;

import lombok.Data;

@Data
public class TakeCellRequest {
    private Long cellId;
    private Long matchId;
}