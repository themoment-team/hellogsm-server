package kr.hellogsm.back_v2.domain.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;
import kr.hellogsm.back_v2.domain.application.service.CreateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final CreateApplicationService createApplicationService;

    @PostMapping("/application")
    public ResponseEntity<Map<String, String>> create(@RequestBody @Valid CreateApplicationReqDto body) throws JsonProcessingException {
        createApplicationService.execute(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "생성되었습니다"));
    }
}
