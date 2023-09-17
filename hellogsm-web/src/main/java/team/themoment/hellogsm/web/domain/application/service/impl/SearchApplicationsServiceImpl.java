package team.themoment.hellogsm.web.domain.application.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.web.domain.application.dto.response.SearchApplicationsResDto;
import team.themoment.hellogsm.web.domain.application.enums.SearchTag;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.SearchApplicationsService;

/**
 * 최종제출이 완료된 사용자를 검색하는 service implementation 입니다.
 */
@Service
@XRayEnabled
@RequiredArgsConstructor
public class SearchApplicationsServiceImpl implements SearchApplicationsService {
    final ApplicationRepository applicationRepository;

    /**
     * tag와 keyword로 원서들을 찾고 해당 원서 정보를 가공하여 반환합니다.
     *
     * @param page 몇번째 페이지를 조회할 것인지 결정합니다. 첫번째 페이지의 인덱스는 0입니다.
     * @param size 한 페이지에 몇개의 원서를 담을 것인지 결정합니다.
     * @param tag 검색 태그입니다.(카테고리)
     * @param keyword 검색 키워드입니다.
     * @return {@link SearchApplicationsResDto} 페이지 정보, (List)원서에 대한 내용
     */
    @Override
    public SearchApplicationsResDto execute(Integer page, Integer size, @Nullable SearchTag tag, @Nullable String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return ApplicationMapper.INSTANCE.applicationsToSearchApplicationsResDto(applicationPage(tag, keyword, pageable));
    }

    public Page<Application> applicationPage(SearchTag tag, String keyword, Pageable pageable) {
        if (tag == null) {
            return applicationRepository.findAllByAdmissionStatusIsFinalSubmitted(true, pageable);
        }
        return switch (tag) {
            case APPLICANT -> applicationRepository.findAllByAdmissionInfoApplicantNameContainingAndAdmissionStatus_IsFinalSubmitted(keyword, true, pageable);
            case SCHOOL -> applicationRepository.findAllByAdmissionInfoSchoolNameContainingAndAdmissionStatus_IsFinalSubmitted(keyword, true, pageable);
            case PHONE_NUMBER -> applicationRepository.findAllByIsFinalSubmittedAndPhoneNumberContaining(keyword, pageable);
        };
    }
}
