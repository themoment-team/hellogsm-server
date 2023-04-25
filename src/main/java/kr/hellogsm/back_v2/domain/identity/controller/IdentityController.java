package kr.hellogsm.back_v2.domain.identity.controller;

import jakarta.validation.Valid;
import kr.hellogsm.back_v2.domain.identity.dto.request.CreateIdentityReqDto;
import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;
import kr.hellogsm.back_v2.domain.identity.service.CreateIdentityService;
import kr.hellogsm.back_v2.domain.identity.service.IdentityQuery;
import kr.hellogsm.back_v2.global.security.oauth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identity/v1")
@RequiredArgsConstructor
public class IdentityController {

    private final CreateIdentityService createIdentityService;
    private final IdentityQuery identityQuery;


    /*
    Identity 생성 기능은 나중에 핸드폰 본인인증 도입했을 때, 삭제 될 예정
    */
    @PostMapping("/identity/{userId}")
    public ResponseEntity<IdentityResDto> createByUserId(
            @RequestBody @Valid CreateIdentityReqDto identityReqDto,
            @PathVariable Long userId
    ) {
        IdentityResDto identityResDto = createIdentityService.execute(identityReqDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @PostMapping("/identity")
    public ResponseEntity<IdentityResDto> create(
            @RequestBody @Valid CreateIdentityReqDto userDto,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        IdentityResDto identityResDto = createIdentityService.execute(userDto, userInfo.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @GetMapping("/identity")
    public ResponseEntity<IdentityResDto> find(
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        IdentityResDto identityResDto = identityQuery.execute(userInfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @GetMapping("/identity/{identityId}")
    public ResponseEntity<IdentityResDto> findByIdentityId(
            @PathVariable Long identityId
    ) {
        IdentityResDto identityResDto = identityQuery.execute(identityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }
}
