package team.themoment.hellogsm.web.domain.application.service.impl;

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

@Service
@RequiredArgsConstructor
public class ApplicationListQueryImpl implements ApplicationListQuery {
    final ApplicationRepository applicationRepository;

    @Override
    public ApplicationListDto execute(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Application> applicationPage = applicationRepository.findAll(pageable);
        return ApplicationMapper.INSTANCE.createApplicationListDto(applicationPage.getContent());
    }
}
