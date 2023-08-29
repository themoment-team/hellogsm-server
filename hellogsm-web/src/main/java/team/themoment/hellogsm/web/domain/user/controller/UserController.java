package team.themoment.hellogsm.web.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.service.DeleteUserService;
import team.themoment.hellogsm.web.domain.user.service.UserByIdQuery;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;

import java.util.Map;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticatedUserManager manager;
    private final UserByIdQuery userByIdQuery;
    private final DeleteUserService deleteUserService;

    @GetMapping("/user/me")
    public ResponseEntity<UserDto> find() {
        UserDto userDto = userByIdQuery.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/user/me")
    public ResponseEntity<Map<String,String>> delete() {
        deleteUserService.execute(manager.getId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("massage","삭제되었습니다."));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> findByUserId(
            @PathVariable Long userId
    ) {
        UserDto userDto = userByIdQuery.execute(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
