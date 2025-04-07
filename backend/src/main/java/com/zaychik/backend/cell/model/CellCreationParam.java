package com.zaychik.backend.cell.model;

import lombok.Builder;

@Builder
public record CellCreationParam(Integer cellNum,
                                Long matchId) {
}
