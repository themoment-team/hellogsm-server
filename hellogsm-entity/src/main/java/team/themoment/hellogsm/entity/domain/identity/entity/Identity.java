package team.themoment.hellogsm.entity.domain.identity.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.hellogsm.entity.domain.application.enums.Gender;

/**
 * 본인인증 정보를 저장하는 Identity Entity입니다.
 *
 * @author 양시준
 * @since 1.0.0
 */
@Entity
@Table(name = "identity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Identity {

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
}
