package team.themoment.hellogsm.entity.domain.user.event;

import team.themoment.hellogsm.entity.domain.user.entity.User;

/**
 * {@link User}를 업데이트하는 이벤트를 나타냅니다. <br/>
 * {@code User}의 삭제를 구독자에게 알리기 위해 사용됩니다.
 */
public record DeleteUserEvent(User user) {
}
