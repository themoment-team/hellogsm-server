package team.themoment.hellogsm.web.domain.identity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;
import team.themoment.hellogsm.web.domain.identity.dto.request.IdentityReqDto;
import team.themoment.hellogsm.web.domain.identity.dto.response.CreateIdentityResDto;
import team.themoment.hellogsm.web.domain.identity.service.CreateIdentityService;
import team.themoment.hellogsm.web.domain.identity.service.IdentityQuery;
import team.themoment.hellogsm.web.domain.identity.service.ModifyIdentityService;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/identity/v1")
@RequiredArgsConstructor
public class IdentityController {
    private final AuthenticatedUserManager manager;
    private final CreateIdentityService createIdentityService;
    private final ModifyIdentityService modifyIdentityService;
    private final IdentityQuery identityQuery;

    // 사용은 안 하지만 혹시 몰라서 남김
//    @PostMapping("/identity/{userId}")
//    public ResponseEntity<IdentityDto> createByUserId(
//            @RequestBody @Valid IdentityReqDto reqDto,
//            @PathVariable Long userId
//    ) {
//        IdentityDto identityResDto = createIdentityService.execute(reqDto, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(identityResDto);
//    }

    @PostMapping("/identity/me")
    public ResponseEntity<Object> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid IdentityReqDto reqDto
    ) {
        CreateIdentityResDto resDto = createIdentityService.execute(reqDto, manager.getId());
        manager.setRole(httpServletRequest, resDto.userRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "본인인증이 완료되었습니다"));
    }

    @GetMapping("/identity/me")
    public ResponseEntity<IdentityDto> find() {
        IdentityDto identityResDto = identityQuery.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }

    @PutMapping("/identity/me")
    public ResponseEntity<Map<String, String>> modify(
            @RequestBody @Valid IdentityReqDto reqDto
    ) {
        modifyIdentityService.execute(reqDto, manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정되었습니다"));
    }

    @GetMapping("/identity/{userId}")
    public ResponseEntity<IdentityDto> findByUserId(
            @PathVariable Long userId
    ) {
        IdentityDto identityResDto = identityQuery.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(identityResDto);
    }
}
