package com.zaychik.web.match.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchFoundResponse {
    private Long matchId;
    private Integer startingCellNum; 
    private List<PlayerPositionDto> enemies; 
}