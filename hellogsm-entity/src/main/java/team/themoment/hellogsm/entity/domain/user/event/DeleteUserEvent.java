package team.themoment.hellogsm.entity.domain.user.event;

import team.themoment.hellogsm.entity.domain.user.entity.User;

public record DeleteUserEvent(User user) {
}
