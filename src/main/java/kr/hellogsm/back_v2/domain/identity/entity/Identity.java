package kr.hellogsm.back_v2.domain.identity.entity;

import jakarta.persistence.*;
import lombok.*;

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
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "user_id", unique = true)
    Long userId;
}
