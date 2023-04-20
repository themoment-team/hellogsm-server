package kr.hellogsm.back_v2.domain.user.service;

public interface ExistUserQuery {
    Boolean execute(String provider, String providerId);
}
