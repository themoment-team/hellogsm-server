package kr.hellogsm.back_v2.domain.application.controller;

import jakarta.validation.Valid;
import kr.hellogsm.back_v2.domain.application.dto.request.CreateApplicationReqDto;
import kr.hellogsm.back_v2.domain.application.service.CreateApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void create(@RequestBody @Valid CreateApplicationReqDto body) {
        createApplicationService.execute(body);
    }
}
