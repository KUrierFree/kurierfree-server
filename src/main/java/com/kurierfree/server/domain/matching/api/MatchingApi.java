package com.kurierfree.server.domain.matching.api;

import com.kurierfree.server.domain.matching.application.MatchingService;
import com.kurierfree.server.domain.matching.dto.request.MatchingRequest;
import com.kurierfree.server.domain.matching.dto.response.MatchingResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class MatchingApi {
    private final MatchingService matchingService;

    public MatchingApi(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/matchings")
    @Operation(summary = "매칭 확정")
    public ResponseEntity<String> createMatching(@RequestBody MatchingRequest matchingRequest) {
        try{
            matchingService.createMatching(matchingRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("매칭이 성공적으로 완료되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/disabled-students/{disabledStudentsId}/matchings")
    @Operation(summary = "장애학생 별 서포터즈 매칭 3순위 조회")
    public ResponseEntity<MatchingResponse> getMatchingThirdPriorityOfDisabledStudent(@PathVariable Long disabledStudentsId) {
        try{
            MatchingResponse matchingResponse = matchingService.getMatchingThirdPriorityOfDisabledStudent(disabledStudentsId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(matchingResponse);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
