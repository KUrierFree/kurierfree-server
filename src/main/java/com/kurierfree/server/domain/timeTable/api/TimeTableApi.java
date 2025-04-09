package com.kurierfree.server.domain.timeTable.api;

import com.kurierfree.server.domain.timeTable.application.TimeTableService;
import com.kurierfree.server.domain.timeTable.dto.response.TimeTableResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TimeTableApi {
    private final TimeTableService timeTableService;

    public TimeTableApi(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping("/admin/users/{userId}/timetables")
    @Operation(summary = "시간표 가져오기")
    public ResponseEntity<TimeTableResponse> getTimeTableWithUserId(@PathVariable Long userId) {
        try{
            TimeTableResponse timeTable = timeTableService.getTimeTableWithUserId(userId);
            return ResponseEntity.ok(timeTable);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/timetables")
    @Operation(summary = "시간표 가져오기")
    public ResponseEntity<TimeTableResponse> getTimeTableWithToken(@RequestHeader("Authorization") String token) {
        try{
            TimeTableResponse timeTable = timeTableService.getTimeTableWithToken(token);
            return ResponseEntity.ok(timeTable);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
