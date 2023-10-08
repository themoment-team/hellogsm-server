package team.themoment.hellogsm.entity.domain.identity.event;

import team.themoment.hellogsm.entity.domain.identity.entity.Identity;

/**
 * {@link Identity}를 업데이트하는 이벤트를 나타냅니다. <br/>
 * {@code Identity}의 변경을 구독자에게 알리기 위해 사용됩니다.
 */
public record UpdateIdentityEvent(Identity identity) {}
