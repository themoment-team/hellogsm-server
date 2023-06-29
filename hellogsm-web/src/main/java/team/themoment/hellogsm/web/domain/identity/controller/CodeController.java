package team.themoment.hellogsm.web.domain.identity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.GenerateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.service.AuthenticateCodeService;
import team.themoment.hellogsm.web.domain.identity.service.GenerateCodeService;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/identity/v1")
@RequiredArgsConstructor
public class CodeController {
    private final AuthenticatedUserManager manager;
    private final GenerateCodeService generateCodeService;
    private final AuthenticateCodeService authenticateCodeService;

    @PostMapping("/identity/me/send-code")
    public ResponseEntity<Map> sendCode(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        generateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "전송되었습니다."));
    }

    @PostMapping("/identity/me/send-code-test")
    public ResponseEntity<Map> sendCodeTest(
            @RequestBody @Valid GenerateCodeReqDto reqDto
    ) {
        var code = generateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "전송되었습니다. : " + code));
    }

    @PostMapping("/identity/me/auth-code")
    public ResponseEntity<Map> authCode(
            @RequestBody @Valid AuthenticateCodeReqDto reqDto
    ) {
        authenticateCodeService.execute(manager.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "인증되었습니다."));
    }
}
