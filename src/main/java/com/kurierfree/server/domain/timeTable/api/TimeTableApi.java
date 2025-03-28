package com.kurierfree.server.domain.timeTable.api;

import com.kurierfree.server.domain.timeTable.application.TimeTableService;
import com.kurierfree.server.domain.timeTable.dto.response.TimeTableResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class TimeTableApi {
    private final TimeTableService timeTableService;

    public TimeTableApi(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping("/timetable")
    @Operation(summary = "시간표 가져오기")
    public ResponseEntity<TimeTableResponse> getTimeTable(@RequestHeader("Authorization") String token) {
        try{
            TimeTableResponse timeTable = timeTableService.getTimeTable(token);
            return ResponseEntity.ok(timeTable);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
