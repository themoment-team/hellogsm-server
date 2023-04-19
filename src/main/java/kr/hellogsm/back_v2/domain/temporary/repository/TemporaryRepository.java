package kr.hellogsm.back_v2.domain.temporary.repository;

import kr.hellogsm.back_v2.domain.temporary.entity.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryRepository extends JpaRepository<Temporary, Long> {
}
