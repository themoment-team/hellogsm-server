package kr.hellogsm.back_v2.domain.temporary.service.impl;

import kr.hellogsm.back_v2.domain.temporary.repository.TemporaryRepository;
import kr.hellogsm.back_v2.domain.temporary.service.ExistTemporaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExistTemporaryServiceImpl implements ExistTemporaryService {

    private final TemporaryRepository temporaryRepository;

    @Override
    public Boolean execute(String provider, String providerId) {
        return temporaryRepository.existsByProviderAndProviderId(provider, providerId);
    }
}
