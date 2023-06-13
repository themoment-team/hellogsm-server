package team.themoment.hellogsm.web.domain.application.controller;

import jakarta.validation.Valid;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.service.CreateApplicationService;
import team.themoment.hellogsm.web.domain.application.service.ModifyApplicationService;
import team.themoment.hellogsm.web.domain.application.service.QuerySingleApplicationService;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 원서를 관리하는 controller 입니다
 *
 * @author 변찬우
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/application/v1")
public class ApplicationController {
    private final AuthenticatedUserManager manager;
    private final CreateApplicationService createApplicationService;
    private final ModifyApplicationService modifyApplicationService;
    private final QuerySingleApplicationService querySingleApplicationService;

    @GetMapping("/application/{applicationId}")
    public SingleApplicationRes readOne(@PathVariable("applicationId") Long applicationId) {
        return querySingleApplicationService.execute(applicationId);
    }

    @GetMapping("/application")
    public SingleApplicationRes readMe() {
        return querySingleApplicationService.execute(manager.getId());
    }

    @PostMapping("/application")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody @Valid ApplicationReqDto body
    ) {
        createApplicationService.execute(body, manager.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "생성되었습니다"));
    }

    @PutMapping("/application")
    public ResponseEntity<Map<String, String>> modify(
            @RequestBody @Valid ApplicationReqDto body
    ) {
        modifyApplicationService.execute(body, manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다"));
    }
}
