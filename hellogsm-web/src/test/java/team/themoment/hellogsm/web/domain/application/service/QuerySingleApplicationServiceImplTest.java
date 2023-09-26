package team.themoment.hellogsm.web.domain.application.service;

import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.entity.grade.MiddleSchoolGrade;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.*;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GedAdmissionGradeDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.SuperGrade;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.impl.QuerySingleApplicationServiceImpl;
import team.themoment.hellogsm.web.global.exception.error.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class QuerySingleApplicationServiceImplTest {

    @InjectMocks
    private QuerySingleApplicationServiceImpl querySingleApplicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    private final DesiredMajor desiredMajor = DesiredMajor.builder()
            .firstDesiredMajor(Major.SW)
            .secondDesiredMajor(Major.AI)
            .thirdDesiredMajor(Major.IOT)
            .build();

    private final AdmissionInfo admissionInfoDummy = AdmissionInfo.builder()
            .id(1L)
            .applicantImageUri("https://naver.com")
            .applicantName("차무식")
            .applicantGender(Gender.MALE)
            .applicantBirth(LocalDate.EPOCH)
            .address("지원자 집주소")
            .detailAddress("지원자 상세주소")
            .graduation(GraduationStatus.GED)
            .telephone("01012345678")
            .applicantPhoneNumber("01012345678")
            .guardianName("마동석")
            .relationWithApplicant("부")
            .guardianPhoneNumber("01012345678")
            .teacherName("강해린")
            .teacherPhoneNumber("01012345678")
            .screening(Screening.GENERAL)
            .schoolName("지원자 학교 이름")
            .schoolLocation("지원자 학교 위치")
            .desiredMajor(desiredMajor)
            .build();

    private final AdmissionStatus admissionStatusDummy = AdmissionStatus.builder()
            .id(1L)
            .isFinalSubmitted(true)
            .isPrintsArrived(true)
            .firstEvaluation(EvaluationStatus.PASS)
            .secondEvaluation(EvaluationStatus.NOT_YET)
            .screeningFirstEvaluationAt(Screening.GENERAL)
            .screeningSecondEvaluationAt(null)
            .registrationNumber(null)
            .secondScore(null)
            .finalMajor(Major.SW)
            .build();

    private final MiddleSchoolGrade middleSchoolGradeDummy = new MiddleSchoolGrade(
            1L,
            "{\"curriculumScoreSubtotal\":100,\"nonCurriculumScoreSubtotal\":100,\"rankPercentage\":0,\"scoreTotal\":261}"
    );

    private final Application applicationDummy = new Application(
            1L,
            admissionInfoDummy,
            admissionStatusDummy,
            middleSchoolGradeDummy,
            1L
    );

    @Test
    public void 성공() {
        // given
        given(applicationRepository.findByUserIdEagerFetch(any(Long.class))).willReturn(Optional.of(applicationDummy));

        // when & then
        assertDoesNotThrow(() -> querySingleApplicationService.execute(1L));
    }

    @Test
    public void 존재하지_않는_User() {
        // given
        given(applicationRepository.findByUserIdEagerFetch(any(Long.class))).willReturn(Optional.empty());

        // when & then
        assertExpectedExceptionWithMessage("존재하지 않는 유저입니다");
    }

    private void assertExpectedExceptionWithMessage(String expectedMessage) {
        ExpectedException exception = assertThrows(ExpectedException.class, () ->
                querySingleApplicationService.execute(1L));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
