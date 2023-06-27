package team.themoment.hellogsm.web.domain.identity.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(timeToLive = 3600L) // 60 * 60 = 3600 = 1 hour
public class AuthenticationCode {
    @Id
    private String code;
    @Indexed
    private Long userId;
    private Boolean authenticated;
    private LocalDateTime createdAt; // @RedisHash 의 경우 @CreateAt이나 @PrePersist가 안돼서 저장하기 직전에 직접 생성해서 넣어줄 것
}
