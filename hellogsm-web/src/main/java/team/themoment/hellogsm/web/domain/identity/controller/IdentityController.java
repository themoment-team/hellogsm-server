package team.themoment.hellogsm.web.domain.identity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.AuthenticateCodeReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.service.AuthenticateCodeService;
import team.themoment.hellogsm.web.domain.identity.service.CreateIdentityService;
import team.themoment.hellogsm.web.domain.identity.service.GenerateCodeService;
import team.themoment.hellogsm.web.domain.identity.service.IdentityQuery;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/identity/v1")
@RequiredArgsConstructor
public class IdentityController {
    private final AuthenticatedUserManager manager;
    private final CreateIdentityService createIdentityService;
    private final GenerateCodeService generateCodeService;
    private final AuthenticateCodeService authenticateCodeService;
    private final IdentityQuery identityQuery;

    @PostMapping("/identity/{userId}")
    public ResponseEntity<IdentityDto> createByUserId(
            @RequestBody @Valid IdentityReqDto reqDto,
            @PathVariable Long userId
    ) {
        IdentityDto identityResDto = createIdentityService.execute(reqDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @PostMapping("/identity/me")
    public ResponseEntity<Object> create(
            @RequestBody @Valid IdentityReqDto reqDto
    ) {
        createIdentityService.execute(reqDto, manager.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/auth/v1/logout"));
        // 인증정보 갱신을 위한 로그아웃 uri로 리다이렉트
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/identity/me")
    public ResponseEntity<IdentityDto> find() {
        IdentityDto identityResDto = identityQuery.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @GetMapping("/identity/{userId}")
    public ResponseEntity<IdentityDto> findByUserId(
            @PathVariable Long userId
    ) {
        IdentityDto identityResDto = identityQuery.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @GetMapping("/identity/me/send-code")
    public ResponseEntity<Map> sendCode() {
        generateCodeService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "전송되었습니다."));
    }

    @GetMapping("/identity/me/send-code-test")
    public ResponseEntity<Map> sendCodeTest() {
        var code = generateCodeService.execute(manager.getId());
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
