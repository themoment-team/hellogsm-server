package kr.hellogsm.back_v2.domain.user.controller;

import kr.hellogsm.back_v2.domain.user.dto.domain.UserDto;
import kr.hellogsm.back_v2.domain.user.service.UserByIdQuery;
import kr.hellogsm.back_v2.global.security.oauth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserByIdQuery userByIdQuery;

    @GetMapping("/user")
    public ResponseEntity<UserDto> find(
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        UserDto userDto = userByIdQuery.execute(userInfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> findByUserId(
            @PathVariable Long userId
    ) {
        UserDto userDto = userByIdQuery.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
