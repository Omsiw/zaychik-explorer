package com.zaychik.web.action.dto;

import lombok.Data;

@Data
public class MoveRequest {
    private Integer targetCellNum;
    private Long matchId;
}