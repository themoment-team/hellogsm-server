package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.dto.response.ApplicationListDto;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.ApplicationListQuery;

/**
 * 모든 원서를 paging하여 조회하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class ApplicationListQueryImpl implements ApplicationListQuery {
    final ApplicationRepository applicationRepository;

    /**
     * 원서들을 pagination하여 반환합니다.
     *
     * @param page 몇번째 페이지를 조회할 것인지 결정합니다. 첫번째 페이지의 인덱스는 0입니다.
     * @param size 한 페이지에 몇개의 원서를 담을 것인지 결정합니다.
     * @return {@link  ApplicationListDto} 페이지 정보, (List)원서에 대한 내용
     */
    @Override
    public ApplicationListDto execute(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Application> applicationPage = applicationRepository.findAll(pageable);
        return ApplicationMapper.INSTANCE.createApplicationListDto(applicationPage);
    }
}
