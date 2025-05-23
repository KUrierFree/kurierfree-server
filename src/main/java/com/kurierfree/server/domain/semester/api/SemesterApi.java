package com.kurierfree.server.domain.semester.api;

import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.semester.dto.request.RecruitmentPeriodRequest;
import com.kurierfree.server.domain.semester.dto.response.RecruitmentPeriodResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/semester/{semesterId}")
@RequiredArgsConstructor
public class SemesterApi {
    private final SemesterService semesterService;

    @Operation(summary = "지원 기간 설정")
    @PostMapping("/application-period")
    public ResponseEntity<?> setApplicationPeriod(@PathVariable Long semesterId, @RequestBody RecruitmentPeriodRequest recruitmentPeriodRequest) {
        try {
            semesterService.setApplicationPeriod(semesterId, recruitmentPeriodRequest);
            return ResponseEntity.ok("지원 기간이 설정되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "선발 기간 설정")
    @PostMapping("/selection-period")
    public ResponseEntity<?> setSelectionPeriod(@PathVariable Long semesterId, @RequestBody RecruitmentPeriodRequest recruitmentPeriodRequest) {
        try {
            semesterService.setSelectionPeriod(semesterId, recruitmentPeriodRequest);
            return ResponseEntity.ok("선발 기간이 설정되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @Operation(summary = "지원 기간, 선발 기간 조회",
            description = "isSelectionEndAfterNow = true 면, 마감날이 현재 날짜와 동일하거나 이후라는 뜻")
    @GetMapping("/recruitment-period")
    public ResponseEntity<RecruitmentPeriodResponse> getRecruitmentPeriod(@PathVariable Long semesterId) {
        try {
            RecruitmentPeriodResponse recruitmentPeriodResponse = semesterService.getRecruitmentPeriod(semesterId);
            return ResponseEntity.ok(recruitmentPeriodResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
