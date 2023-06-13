package team.themoment.hellogsm.web.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.hellogsm.web.domain.user.dto.domain.UserDto;
import team.themoment.hellogsm.web.domain.user.service.UserByIdQuery;
import team.themoment.hellogsm.web.global.security.auth.AuthenticatedUserManager;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticatedUserManager manager;
    private final UserByIdQuery userByIdQuery;

    @GetMapping("/user/me")
    public ResponseEntity<UserDto> find() {
        UserDto userDto = userByIdQuery.execute(manager.getId());
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
