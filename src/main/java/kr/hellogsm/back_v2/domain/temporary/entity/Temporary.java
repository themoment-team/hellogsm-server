package kr.hellogsm.back_v2.domain.temporary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temporary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Temporary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporary_id")
    Long id;

    @Column(name = "provider")
    String provider;

    @Column(name = "provider_id;")
    Long providerId;
}