package team.themoment.hellogsm.entity.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.hellogsm.entity.domain.user.enums.Role;

/**
 * 인증/인가 정보를 저장하는 Entity입니다.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @PrePersist
    private void prePersist() {
        this.role = this.role == null ? Role.ROLE_UNAUTHENTICATED : this.role;
    }
}
