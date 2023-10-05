package team.themoment.hellogsm.entity.domain.identity.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;
import team.themoment.hellogsm.entity.domain.identity.event.UpdateIdentityEvent;

/**
 * 본인인증 정보를 저장하는 Identity Entity입니다.
 */
@Entity
@Table(name = "identities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Identity extends AbstractAggregateRoot<Identity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identity_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "user_id", unique = true)
    private Long userId;

    public Identity(Long id, String name, String phoneNumber, LocalDate birth, Gender gender, Long userId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.gender = gender;
        this.userId = userId;
        this.publishEvent();
    }

    public void publishEvent() {
        this.registerEvent(new UpdateIdentityEvent(this));
    }
}
