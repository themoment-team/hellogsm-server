package kr.hellogsm.back_v2.domain.identity.entity;

import jakarta.persistence.*;
import lombok.*;

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
