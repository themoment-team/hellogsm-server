package team.themoment.hellogsm.web.domain.identity.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import team.themoment.hellogsm.web.domain.identity.domain.AuthenticationCode;

public interface CodeRepository extends CrudRepository<AuthenticationCode, String> {
    List<AuthenticationCode> findByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
