package com.kurierfree.server.domain.list.api;

import com.kurierfree.server.domain.list.application.ListService;
import com.kurierfree.server.domain.list.dto.response.DisabledStudentsResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<DisabledStudentsResponse>> getDisabledStudentsList(@RequestHeader("Authorization") String token){
        try{
            List<DisabledStudentsResponse> disabledStudentsResponse = listService.getDisabledStudentsList(token);
            return ResponseEntity.ok(disabledStudentsResponse);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }


    // 서포터즈 명단 조회 /supporters
    // @GetMapping("/supporters")

}
