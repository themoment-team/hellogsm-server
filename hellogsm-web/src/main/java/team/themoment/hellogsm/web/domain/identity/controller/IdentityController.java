package team.themoment.hellogsm.web.domain.identity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.CreateIdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.service.CreateIdentityService;
import team.themoment.hellogsm.web.domain.identity.service.IdentityQuery;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;
import team.themoment.hellogsm.web.global.security.oauth.UserInfo;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/identity/v1")
@RequiredArgsConstructor
public class IdentityController {
    private final AuthenticatedUserManager manager;
    private final CreateIdentityService createIdentityService;
    private final IdentityQuery identityQuery;

    /*
        Identity 생성 기능은 나중에 핸드폰 본인인증 도입했을 때, 삭제 될 예정
    */
    @PostMapping("/identity/{userId}")
    public ResponseEntity<IdentityDto> createByUserId(
            @RequestBody @Valid CreateIdentityReqDto identityReqDto,
            @PathVariable Long userId
    ) {
        IdentityDto identityResDto = createIdentityService.execute(identityReqDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @PostMapping("/identity")
    public ResponseEntity<Object> create(
            @RequestBody @Valid CreateIdentityReqDto userDto
    ) {
        createIdentityService.execute(userDto, manager.getId());
        URI redirectUri = null;
        try {
            redirectUri = new URI("/auth/v1/logout");
        } catch (URISyntaxException e) {
            throw new RuntimeException("redirectUri의 syntax가 잘못되었습니다.", e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping("/identity")
    public ResponseEntity<IdentityDto> find() {
        IdentityDto identityResDto = identityQuery.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @GetMapping("/identity/{identityId}")
    public ResponseEntity<IdentityDto> findByIdentityId(
            @PathVariable Long identityId
    ) {
        IdentityDto identityResDto = identityQuery.execute(identityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }
}
