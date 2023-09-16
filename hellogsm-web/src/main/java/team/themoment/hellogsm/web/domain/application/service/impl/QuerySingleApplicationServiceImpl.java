package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.QuerySingleApplicationService;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

/**
 * 사용자의 원서 정보를 가져오는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class QuerySingleApplicationServiceImpl implements QuerySingleApplicationService {
    final private ApplicationRepository applicationRepository;

    /**
     * 매개변수로 userId를 받아 userId에 해당하는 원서를 찾습니다. <br>
     * 찾은 원서를 바탕으로 단일 원서 정보를 반환합니다.
     *
     * @param userId 사용자의 pk값
     * @return {@link SingleApplicationRes} 단일 원서 정보
     * @throws ExpectedException 발생조건은 아래와 같음 <br>
     *      1. 존재하지 않는 유저일 경우 <br>
     */
    @Override
    public SingleApplicationRes execute(Long userId) {
        Application application = applicationRepository.findByUserIdEagerFetch(userId)
                .orElseThrow(() -> new ExpectedException("존재하지 않는 유저입니다", HttpStatus.NOT_FOUND));

        return ApplicationMapper.INSTANCE.createSingleApplicationDto(application);
    }
}
