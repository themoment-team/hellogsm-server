package kr.hellogsm.back_v2.domain.identity.controller;

import jakarta.validation.Valid;
import kr.hellogsm.back_v2.domain.identity.dto.request.CreateIdentityReqDto;
import kr.hellogsm.back_v2.domain.identity.dto.response.IdentityResDto;
import kr.hellogsm.back_v2.domain.identity.service.CreateIdentityService;
import kr.hellogsm.back_v2.domain.identity.service.IdentityQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    public ResponseEntity<IdentityResDto> createByTempId(@RequestBody @Valid CreateIdentityReqDto identityReqDto, @PathVariable Long userId) {
        IdentityResDto identityResDto = createIdentityService.execute(identityReqDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @PostMapping("/identity")
    public ResponseEntity<IdentityResDto> create(@RequestBody @Valid CreateIdentityReqDto userDto) {
        OAuth2User auth = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = (Long) auth.getAttributes().get("user_id");
        IdentityResDto identityResDto = createIdentityService.execute(userDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

    @GetMapping("/identity")
    public ResponseEntity<IdentityResDto> find() {
        OAuth2User auth = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = (Long) auth.getAttributes().get("user_id");
        IdentityResDto identityResDto = identityQuery.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @GetMapping("/identity/{identityId}")
    public ResponseEntity<IdentityResDto> findByUserId(@PathVariable Long identityId) {
        IdentityResDto identityResDto = identityQuery.execute(identityId);
        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
    }

}