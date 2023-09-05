package team.themoment.hellogsm.web.domain.application.service.impl;

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

@Service
@RequiredArgsConstructor
public class SearchApplicationsServiceImpl implements SearchApplicationsService {
    final ApplicationRepository applicationRepository;

    @Override
    public SearchApplicationsResDto execute(Integer page, Integer size, SearchTag tag, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return ApplicationMapper.INSTANCE.applicationsToSearchApplicationsResDto(applicationPage(tag, keyword, pageable).getContent());
    }

    public Page<Application> applicationPage(SearchTag tag, String keyword, Pageable pageable) {
        return switch (tag) {
            case APPLICANT -> applicationRepository.findAllByAdmissionInfoApplicantNameContainingAndAdmissionStatus_IsFinalSubmitted(keyword, true, pageable);
            case SCHOOL -> applicationRepository.findAllByAdmissionInfoSchoolNameContainingAndAdmissionStatus_IsFinalSubmitted(keyword, true, pageable);
            case PHONE_NUMBER -> applicationRepository.findAllByIsFinalSubmittedAndPhoneNumberContaining(keyword, pageable);
        };
    }
}
