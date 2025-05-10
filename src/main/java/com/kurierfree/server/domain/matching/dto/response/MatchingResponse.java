package com.kurierfree.server.domain.matching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MatchingResponse {
    private List<MatchingSupportersResponse> matchingSupporterResponses;
}
