package com.kurierfree.server.domain.list.api;

import com.kurierfree.server.domain.list.application.ListService;
import com.kurierfree.server.domain.list.dto.request.SupporterStatusUpdateRequest;
import com.kurierfree.server.domain.list.dto.response.DisabledStudentResponse;
import com.kurierfree.server.domain.list.dto.response.SupporterListItemResponse;
import com.kurierfree.server.domain.list.dto.response.SupporterResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ListApi {
    private final ListService listService;

    public ListApi(ListService listService) {
        this.listService = listService;
    }

    //장애학생 명단 조회 /disabled-students
    @Operation(summary = "장애학생 명단 조회")
    @GetMapping("/disabled-students")
    public ResponseEntity<List<DisabledStudentResponse>> getDisabledStudentList(@RequestHeader("Authorization") String token) {
        try {
            List<DisabledStudentResponse> disabledStudentsResponse = listService.getDisabledStudentsList(token);
            return ResponseEntity.ok(disabledStudentsResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "확정된 서포터즈 명단 조회")
    @GetMapping("/supporters")
    public ResponseEntity<List<SupporterResponse>> getSupporterList(@RequestHeader("Authorization") String token) {
        try {
            List<SupporterResponse> supporterResponse = listService.getSupportersList(token);
            return ResponseEntity.ok(supporterResponse);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "선발된 서포터즈 명단 조회",
            description = "status: Matching 인 서포터즈 조회")
    @GetMapping("/supporters/matching")
    public ResponseEntity<List<SupporterListItemResponse>> getMatchingSupporterList(@RequestHeader("Authorization") String token){
        try{
            List<SupporterListItemResponse> matchedSupporterResponses = listService.getMatchingSupportersForAdmin(token);
            return ResponseEntity.ok(matchedSupporterResponses);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "서포터즈 지원자 명단 조회 ",
            description = "status: Pending || Rejected || Matching 인 서포터즈 조회," +
                    "매칭 전")
    @GetMapping("/supporters/applied")
    public ResponseEntity<List<SupporterListItemResponse>> getAppliedSupporterList(@RequestHeader("Authorization") String token){
        try{
            List<SupporterListItemResponse> matchedSupporterResponses = listService.getAppliedSupportersForAdmin(token);
            return ResponseEntity.ok(matchedSupporterResponses);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "서포터즈 상태 변경",
            description = "서포터즈 선발 or 탈락 상태 변경")
    @PutMapping("/supporters/status")
    public ResponseEntity<?> updateSupporterStatus(@RequestHeader("Authorization") String token,
                                                   @RequestBody SupporterStatusUpdateRequest request){
        try{
            listService.updateSupporterStatus(token, request);
            return ResponseEntity.ok(Map.of("message", "서포터즈 상태가 성공적으로 업데이트되었습니다."));
        }catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
