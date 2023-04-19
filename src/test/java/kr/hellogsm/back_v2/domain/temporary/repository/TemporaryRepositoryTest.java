package kr.hellogsm.back_v2.domain.temporary.repository;

import kr.hellogsm.back_v2.domain.temporary.entity.Temporary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("TemporaryRepository 테스트")
class TemporaryRepositoryTest {

    @Autowired
    private TemporaryRepository temporaryRepository;

    @Test
    @DisplayName("임시유저 저장 테스트")
    void testSave() {
        // given
        Temporary temporary = new Temporary(null, "google", "1234657865432");

        // when
        Temporary savedTemporary = temporaryRepository.save(temporary);

        // then
        assertEquals(temporary.getProvider(), savedTemporary.getProvider());
        assertEquals(temporary.getProviderId(), savedTemporary.getProviderId());
    }

    @Test
    @DisplayName("임시유저 존재 여부 확인 테스트")
    void testExistsByProviderAndProviderId() {
        // given
        Temporary temporary = new Temporary(null, "google", "1234657865432");
        temporaryRepository.save(temporary);

        // when
        Boolean isExists = temporaryRepository
                .existsByProviderAndProviderId(temporary.getProvider(), temporary.getProviderId());

        // then
        assertTrue(isExists);
    }
}
