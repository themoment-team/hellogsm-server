package kr.hellogsm.back_v2.domain.user.entity;

import jakarta.persistence.*;
import kr.hellogsm.back_v2.domain.temporary.entity.Temporary;
import lombok.*;

@Entity
@Table(name = "`USER`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    String phoneNumber;

    @OneToOne(optional = false) // 설정 추가
    Temporary temporary;

    // Repo에서 FindByTemporary 해서 없으면 본인인증 X
    // 세션에 본인인증 가능 세션 가지고 있으면 Repo에서 있는지 확인하고, 반환 - 동시접속 가능 긱기를 하나로 고정하면 안 생길 문제긴 한데, 혹시 모르니까?
    // User 생성 시, Temporary 찾는 건 SecurityContext에서 Provider, Provider_id 읽고 DB에서 찾기
}
