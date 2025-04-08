package com.kurierfree.server.domain.list.api;

import com.kurierfree.server.domain.list.application.ListService;
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

    @Operation(summary = "전체 서포터즈 명단 조회")
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

    @Operation(summary = "매칭된 서포터즈 명단 조회",
            description = "status: MATCHED 인 서포터즈 조회")
    @GetMapping("/supporters/matched")
    public ResponseEntity<List<SupporterListItemResponse>> getMatchedSupporterList(@RequestHeader("Authorization") String token){
        try{
            List<SupporterListItemResponse> matchedSupporterResponses = listService.getMatchedSupportersForAdmin(token);
            return ResponseEntity.ok(matchedSupporterResponses);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "지원한 서포터즈 명단 조회 ",
            description = "status: PENDING 인 서포터즈 조회")
    @GetMapping("/supporters/pending")
    public ResponseEntity<List<SupporterListItemResponse>> getPendingSupporterList(@RequestHeader("Authorization") String token){
        try{
            List<SupporterListItemResponse> matchedSupporterResponses = listService.getPendingSupportersForAdmin(token);
            return ResponseEntity.ok(matchedSupporterResponses);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
