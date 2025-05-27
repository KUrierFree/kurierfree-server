package com.kurierfree.server.domain.application.api;

import com.kurierfree.server.domain.application.application.ApplicationService;
import com.kurierfree.server.domain.application.dto.request.ApplicationRequest;
import com.kurierfree.server.domain.application.dto.response.ApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationApi {
    private final ApplicationService applicationService;

    @PostMapping("/application")
    @Operation(summary = "지원서 작성")
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationRequest applicationRequest) {
        try {
            ApplicationResponse applicationResponse = applicationService.createApplication(applicationRequest);
            return ResponseEntity.ok(applicationResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

