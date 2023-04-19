package kr.hellogsm.back_v2.domain.temporary.service.impl;

import kr.hellogsm.back_v2.domain.temporary.dto.request.CreateTemporaryReqDto;
import kr.hellogsm.back_v2.domain.temporary.repository.TemporaryRepository;
import kr.hellogsm.back_v2.domain.temporary.service.CreateTemporaryService;
import kr.hellogsm.back_v2.global.exception.error.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class CreateTemporaryServiceImpl implements CreateTemporaryService {

    private final TemporaryRepository temporaryRepository;

    @Override
    public void execute(CreateTemporaryReqDto createTemporaryReqDto) {
        checkExistTemporary(createTemporaryReqDto);
        temporaryRepository.save(createTemporaryReqDto.toEntity());
    }

    private void checkExistTemporary(CreateTemporaryReqDto createTemporaryReqDto) {
        if (temporaryRepository
                .existsByProviderAndProviderId(createTemporaryReqDto.provider(), createTemporaryReqDto.providerId()))
            throw new ExpectedException("이미 존재하는 Temporary 입니다.", HttpStatus.BAD_REQUEST);
    }
}
