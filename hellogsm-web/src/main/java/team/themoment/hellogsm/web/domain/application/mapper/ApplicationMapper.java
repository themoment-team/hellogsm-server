package team.themoment.hellogsm.web.domain.application.mapper;

import io.micrometer.common.lang.Nullable;
import org.hibernate.Hibernate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import team.themoment.hellogsm.entity.common.util.OptionalUtils;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.admission.DesiredMajor;
import team.themoment.hellogsm.entity.domain.application.entity.grade.AdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GraduateAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.MiddleSchoolGrade;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Major;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;
import team.themoment.hellogsm.entity.domain.identity.entity.Identity;
import team.themoment.hellogsm.web.domain.application.dto.domain.*;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsm.web.domain.application.dto.request.ApplicationStatusReqDto;
import team.themoment.hellogsm.web.domain.application.dto.response.*;
import team.themoment.hellogsm.web.domain.identity.dto.domain.IdentityDto;

import java.math.BigDecimal;
import java.util.*;

/**
 * {@link Application}과 관련된 객체를 Mapping하는 역할의 객체입니다.
 */
@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.WARN,
        uses = OptionalUtils.class
)
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);


    default SingleApplicationRes createSingleApplicationDto(Application application) {
        AdmissionInfoDto admissionInfo = ApplicationMapper.INSTANCE.admissionInfoToAdmissionInfoDto(application.getAdmissionInfo());
        AdmissionStatusDto admissionStatus = ApplicationMapper.INSTANCE.admissionStatusToAdmissionStatusDto(application.getAdmissionStatus());
        SuperGrade admissionGrade;


        if (application.getAdmissionInfo().getGraduation().equals(GraduationStatus.GED)) {
            admissionGrade = gedAdmissionGradeToGedAdmissionGradeDto((GedAdmissionGrade) application.getAdmissionGrade());
        } else {
            admissionGrade = graduateAdmissionGradeToAdmissionGradeDto((GraduateAdmissionGrade) application.getAdmissionGrade());
        }

        // TODO admissionGrade가 SuperGrade 타입이면 막기

        return new SingleApplicationRes(
                application.getId(),
                admissionInfo,
                application.getMiddleSchoolGrade().getMiddleSchoolGradeText(),
                admissionGrade,
                admissionStatus
        );
    }

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    @Mappings({
            @Mapping(source = "totalScore", target = "totalScore"),
            @Mapping(source = "percentileRank", target = "percentileRank"),
            @Mapping(source = "grade1Semester1Score", target = "grade1Semester1Score"),
            @Mapping(source = "grade1Semester2Score", target = "grade1Semester2Score"),
            @Mapping(source = "grade2Semester1Score", target = "grade2Semester1Score"),
            @Mapping(source = "grade2Semester2Score", target = "grade2Semester2Score"),
            @Mapping(source = "grade3Semester1Score", target = "grade3Semester1Score"),
            @Mapping(source = "artisticScore", target = "artisticScore"),
            @Mapping(source = "curricularSubtotalScore", target = "curricularSubtotalScore"),
            @Mapping(source = "attendanceScore", target = "attendanceScore"),
            @Mapping(source = "volunteerScore", target = "volunteerScore"),
            @Mapping(source = "extracurricularSubtotalScore", target = "extracurricularSubtotalScore"),
    })
    GeneralAdmissionGradeDto graduateAdmissionGradeToAdmissionGradeDto(GraduateAdmissionGrade admissionGrade);

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    @Mappings({
            @Mapping(source = "totalScore", target = "totalScore"),
            @Mapping(source = "percentileRank", target = "percentileRank"),
            @Mapping(source = "gedTotalScore", target = "gedTotalScore"),
            @Mapping(source = "gedMaxScore", target = "gedMaxScore"),
    })
    GedAdmissionGradeDto gedAdmissionGradeToGedAdmissionGradeDto(GedAdmissionGrade admissionGrade);

    @BeanMapping(ignoreUnmappedSourceProperties = {"id"})
    @Mappings({
            @Mapping(source = "applicantName", target = "applicantName"),
            @Mapping(source = "applicantGender", target = "applicantGender"),
            @Mapping(source = "applicantBirth", target = "applicantBirth"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "detailAddress", target = "detailAddress"),
            @Mapping(source = "graduation", target = "graduation"),
            @Mapping(source = "telephone", target = "telephone"),
            @Mapping(source = "applicantPhoneNumber", target = "applicantPhoneNumber"),
            @Mapping(source = "guardianName", target = "guardianName"),
            @Mapping(source = "relationWithApplicant", target = "relationWithApplicant"),
            @Mapping(source = "guardianPhoneNumber", target = "guardianPhoneNumber"),
            @Mapping(source = "teacherName", target = "teacherName"),
            @Mapping(source = "teacherPhoneNumber", target = "teacherPhoneNumber"),
            @Mapping(source = "schoolName", target = "schoolName"),
            @Mapping(source = "schoolLocation", target = "schoolLocation"),
            @Mapping(source = "applicantImageUri", target = "applicantImageUri"),
            @Mapping(source = "screening", target = "screening"),
            @Mapping(source = "desiredMajor", target = "desiredMajor"),
    })
    AdmissionInfoDto admissionInfoToAdmissionInfoDto(AdmissionInfo admissionInfo);

    @BeanMapping(ignoreUnmappedSourceProperties = {"id", "isFinalSubmitted", "isPrintsArrived"})
    @Mappings({
            @Mapping(source = "finalSubmitted", target = "isFinalSubmitted"),
            @Mapping(source = "printsArrived", target = "isPrintsArrived"),
            @Mapping(source = "firstEvaluation", target = "firstEvaluation"),
            @Mapping(source = "secondEvaluation", target = "secondEvaluation"),
            @Mapping(source = "screeningFirstEvaluationAt", target = "screeningFirstEvaluationAt"),
            @Mapping(source = "screeningSecondEvaluationAt", target = "screeningSecondEvaluationAt"),
            @Mapping(source = "registrationNumber", target = "registrationNumber"),
            @Mapping(source = "secondScore", target = "secondScore"),
            @Mapping(source = "finalMajor", target = "finalMajor"),
    })
    AdmissionStatusDto admissionStatusToAdmissionStatusDto(AdmissionStatus admissionStatus);

    @BeanMapping(ignoreUnmappedSourceProperties = {"id", "middleSchoolGrade", "admissionGrade", "userId"})
    @Mappings({
            @Mapping(source = "admissionInfo.applicantName", target = "applicantName"),
            @Mapping(source = "admissionInfo.applicantBirth", target = "applicantBirth"),
            @Mapping(source = "admissionInfo.applicantImageUri", target = "applicantImageUri"),
            @Mapping(source = "admissionInfo.schoolName", target = "schoolName"),
            @Mapping(source = "admissionInfo.screening", target = "screening"),
            @Mapping(source = "admissionStatus.registrationNumber", target = "registrationNumber"),
    })
    TicketResDto applicationToTicketResDto(Application application);

    List<TicketResDto> applicationListToTicketResDtos(List<Application> applicationList);

    default ApplicationListDto createApplicationListDto(Page<Application> applications) {
        return new ApplicationListDto(
                new ApplicationListInfoDto(applications.getTotalPages(), applications.getTotalElements()),
                applicationListToApplicationsDtoList(applications.toList())
        );
    }

    @BeanMapping(ignoreUnmappedSourceProperties = {"middleSchoolGrade", "admissionGrade", "userId"})
    @Mappings({
            @Mapping(source = "id", target = "applicationId"),
            @Mapping(source = "admissionInfo.applicantName", target = "applicantName"),
            @Mapping(source = "admissionInfo.graduation", target = "graduation"),
            @Mapping(source = "admissionInfo.applicantPhoneNumber", target = "applicantPhoneNumber"),
            @Mapping(source = "admissionInfo.guardianPhoneNumber", target = "guardianPhoneNumber"),
            @Mapping(source = "admissionInfo.teacherName", target = "teacherName"),
            @Mapping(source = "admissionInfo.teacherPhoneNumber", target = "teacherPhoneNumber"),
            @Mapping(source = "admissionStatus.isFinalSubmitted", target = "isFinalSubmitted"),
            @Mapping(source = "admissionStatus.isPrintsArrived", target = "isPrintsArrived"),
            @Mapping(source = "admissionStatus.firstEvaluation", target = "firstEvaluation"),
            @Mapping(source = "admissionStatus.secondEvaluation", target = "secondEvaluation"),
            @Mapping(source = "admissionStatus.screeningFirstEvaluationAt", target = "screeningFirstEvaluationAt"),
            @Mapping(source = "admissionStatus.screeningSecondEvaluationAt", target = "screeningSecondEvaluationAt"),
            @Mapping(source = "admissionStatus.registrationNumber", target = "registrationNumber"),
            @Mapping(source = "admissionStatus.secondScore", target = "secondScore"),
    })
    ApplicationsDto applicationToApplicationsDto(Application applicationList);

    default AdmissionStatus createNewAdmissionStatus(Long admissionStatusId, ApplicationStatusReqDto applicationStatusReqDto) {
        Major finalMajor = null;
        Screening screeningFirstEvaluationAt = null;
        Screening screeningSecondEvaluationAt = null;

        try {
            if (applicationStatusReqDto.finalMajor() != null)
                finalMajor = Major.valueOf(applicationStatusReqDto.finalMajor());
            if (applicationStatusReqDto.screeningFirstEvaluationAt() != null)
                screeningFirstEvaluationAt = Screening.valueOf(applicationStatusReqDto.screeningFirstEvaluationAt());
            if (applicationStatusReqDto.screeningSecondEvaluationAt() != null)
                screeningSecondEvaluationAt = Screening.valueOf(applicationStatusReqDto.screeningSecondEvaluationAt());
        } catch (Exception ex) {
            throw new RuntimeException("예상하지 못한 에러 발생", ex);
        }

        return AdmissionStatus.builder()
                .id(admissionStatusId)
                .isPrintsArrived(applicationStatusReqDto.isPrintsArrived())
                .firstEvaluation(EvaluationStatus.valueOf(applicationStatusReqDto.firstEvaluation()))
                .secondEvaluation(EvaluationStatus.valueOf(applicationStatusReqDto.secondEvaluation()))
                .isFinalSubmitted(applicationStatusReqDto.isFinalSubmitted())
                .registrationNumber(applicationStatusReqDto.registrationNumber())
                .screeningFirstEvaluationAt(screeningFirstEvaluationAt)
                .screeningSecondEvaluationAt(screeningSecondEvaluationAt)
                .finalMajor(finalMajor)
                .secondScore(applicationStatusReqDto.secondScore())
                .build();
    }

    List<ApplicationsDto> applicationListToApplicationsDtoList(List<Application> applicationList);


    default AdmissionStatus updateFinalSubmission(AdmissionStatus admissionStatus) {
        return AdmissionStatus.builder()
                .id(admissionStatus.getId())
                .isFinalSubmitted(true)
                .isPrintsArrived(admissionStatus.isPrintsArrived())
                .firstEvaluation(admissionStatus.getFirstEvaluation())
                .secondEvaluation(admissionStatus.getSecondEvaluation())
                .registrationNumber(OptionalUtils.fromOptional(admissionStatus.getRegistrationNumber()))
                .screeningFirstEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningFirstEvaluationAt()))
                .screeningSecondEvaluationAt(OptionalUtils.fromOptional(admissionStatus.getScreeningSecondEvaluationAt()))
                .secondScore(OptionalUtils.fromOptional(admissionStatus.getSecondScore()))
                .finalMajor(OptionalUtils.fromOptional(admissionStatus.getFinalMajor()))
                .build();
    }


    default Application applicationReqDtoAndIdentityDtoToApplication(ApplicationReqDto applicationReqDto, IdentityDto identityDto, Long userId, @Nullable Long appId) {

        DesiredMajor desiredMajor = DesiredMajor.builder()
                .firstDesiredMajor(Major.valueOf(applicationReqDto.firstDesiredMajor()))
                .secondDesiredMajor(Major.valueOf(applicationReqDto.secondDesiredMajor()))
                .thirdDesiredMajor(Major.valueOf(applicationReqDto.thirdDesiredMajor()))
                .build();

        AdmissionInfo admissionInfo = AdmissionInfo.builder()
                .id(userId)
                .applicantImageUri(applicationReqDto.applicantImageUri())
                .applicantName(identityDto.name())
                .applicantGender(identityDto.gender())
                .applicantBirth(identityDto.birth())
                .address(applicationReqDto.address())
                .detailAddress(applicationReqDto.detailAddress())
                .graduation(GraduationStatus.valueOf(applicationReqDto.graduation()))
                .telephone(applicationReqDto.telephone())
                .applicantPhoneNumber(identityDto.phoneNumber())
                .guardianName(applicationReqDto.guardianName())
                .relationWithApplicant(applicationReqDto.relationWithApplicant())
                .guardianPhoneNumber(applicationReqDto.guardianPhoneNumber())
                .teacherName(applicationReqDto.teacherName())
                .teacherPhoneNumber(applicationReqDto.teacherPhoneNumber())
                .screening(Screening.valueOf(applicationReqDto.screening()))
                .schoolName(applicationReqDto.schoolName())
                .schoolLocation(applicationReqDto.schoolLocation())
                .desiredMajor(desiredMajor)
                .build();

        AdmissionStatus admissionStatus = AdmissionStatus.init(userId);
        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(userId, applicationReqDto.middleSchoolGrade());

        return new Application(
                appId,
                admissionInfo,
                admissionStatus,
                middleSchoolGrade,
                userId
        );
    }

    default Application updateApplicationByApplicationReqDtoAndApplication(ApplicationReqDto applicationReqDto, Application application) {

        Long appId = application.getId();
        Long userId = application.getUserId();
        AdmissionInfo oldAdmissionInfo = application.getAdmissionInfo();
        AdmissionStatus oldAdmissionStatus = application.getAdmissionStatus();


        DesiredMajor desiredMajor = DesiredMajor.builder()
                .firstDesiredMajor(Major.valueOf(applicationReqDto.firstDesiredMajor()))
                .secondDesiredMajor(Major.valueOf(applicationReqDto.secondDesiredMajor()))
                .thirdDesiredMajor(Major.valueOf(applicationReqDto.thirdDesiredMajor()))
                .build();

        AdmissionInfo admissionInfo = AdmissionInfo.builder()
                .id(userId)
                .applicantImageUri(applicationReqDto.applicantImageUri())
                .applicantName(oldAdmissionInfo.getApplicantName())
                .applicantGender(oldAdmissionInfo.getApplicantGender())
                .applicantBirth(oldAdmissionInfo.getApplicantBirth())
                .address(applicationReqDto.address())
                .detailAddress(applicationReqDto.detailAddress())
                .graduation(GraduationStatus.valueOf(applicationReqDto.graduation()))
                .telephone(applicationReqDto.telephone())
                .applicantPhoneNumber(oldAdmissionInfo.getApplicantPhoneNumber())
                .guardianName(applicationReqDto.guardianName())
                .relationWithApplicant(applicationReqDto.relationWithApplicant())
                .guardianPhoneNumber(applicationReqDto.guardianPhoneNumber())
                .teacherName(applicationReqDto.teacherName())
                .teacherPhoneNumber(applicationReqDto.teacherPhoneNumber())
                .screening(Screening.valueOf(applicationReqDto.screening()))
                .schoolName(applicationReqDto.schoolName())
                .schoolLocation(applicationReqDto.schoolLocation())
                .desiredMajor(desiredMajor)
                .build();

        AdmissionStatus admissionStatus = AdmissionStatus
                .builder()
                .id(oldAdmissionStatus.getId())
                .isFinalSubmitted(oldAdmissionStatus.isFinalSubmitted())
                .isPrintsArrived(oldAdmissionStatus.isPrintsArrived())
                .firstEvaluation(oldAdmissionStatus.getFirstEvaluation())
                .secondEvaluation(oldAdmissionStatus.getSecondEvaluation())
                .screeningFirstEvaluationAt(OptionalUtils.fromOptional(oldAdmissionStatus.getScreeningFirstEvaluationAt()))
                .screeningSecondEvaluationAt(OptionalUtils.fromOptional(oldAdmissionStatus.getScreeningFirstEvaluationAt()))
                .registrationNumber(OptionalUtils.fromOptional(oldAdmissionStatus.getRegistrationNumber()))
                .secondScore(OptionalUtils.fromOptional(oldAdmissionStatus.getSecondScore()))
                .finalMajor(OptionalUtils.fromOptional(oldAdmissionStatus.getFinalMajor()))
                .build();

        MiddleSchoolGrade middleSchoolGrade = new MiddleSchoolGrade(userId, applicationReqDto.middleSchoolGrade());

        return new Application(
                appId,
                admissionInfo,
                admissionStatus,
                middleSchoolGrade,
                userId
        );
    }

    @BeanMapping(ignoreUnmappedSourceProperties = {
            "userId",
            "applicantName",
            "applicantGender",
            "applicantBirth",
            "applicantPhoneNumber"
    })
    @Mappings({
            @Mapping(source = "admissionInfo.id", target = "id"),
            @Mapping(source = "admissionInfo.applicantImageUri", target = "applicantImageUri"),
            @Mapping(source = "identity.name", target = "applicantName"),
            @Mapping(source = "identity.gender", target = "applicantGender"),
            @Mapping(source = "identity.birth", target = "applicantBirth"),
            @Mapping(source = "admissionInfo.address", target = "address"),
            @Mapping(source = "admissionInfo.detailAddress", target = "detailAddress"),
            @Mapping(source = "admissionInfo.telephone", target = "telephone"),
            @Mapping(source = "identity.phoneNumber", target = "applicantPhoneNumber"),
            @Mapping(source = "admissionInfo.guardianName", target = "guardianName"),
            @Mapping(source = "admissionInfo.relationWithApplicant", target = "relationWithApplicant"),
            @Mapping(source = "admissionInfo.guardianPhoneNumber", target = "guardianPhoneNumber"),
            @Mapping(source = "admissionInfo.screening", target = "screening"),
            @Mapping(source = "admissionInfo.schoolName", target = "schoolName"),
            @Mapping(source = "admissionInfo.schoolLocation", target = "schoolLocation"),
            @Mapping(source = "admissionInfo.teacherName", target = "teacherName"),
            @Mapping(source = "admissionInfo.teacherPhoneNumber", target = "teacherPhoneNumber"),
            @Mapping(source = "admissionInfo.desiredMajor.firstDesiredMajor", target = "desiredMajor.firstDesiredMajor"),
            @Mapping(source = "admissionInfo.desiredMajor.secondDesiredMajor", target = "desiredMajor.secondDesiredMajor"),
            @Mapping(source = "admissionInfo.desiredMajor.thirdDesiredMajor", target = "desiredMajor.thirdDesiredMajor"),
    })
    AdmissionInfo toConsistentAdmissionInfoWithIdentity(AdmissionInfo admissionInfo, Identity identity);

    @BeanMapping(ignoreUnmappedSourceProperties = {"id", "middleSchoolGrade", "admissionGrade", "userId"})
    @Mappings({
            @Mapping(source = "admissionInfo.id", target = "applicationId"),
            @Mapping(source = "admissionStatus.isFinalSubmitted", target = "isFinalSubmitted"),
            @Mapping(source = "admissionStatus.isPrintsArrived", target = "isPrintsArrived"),
            @Mapping(source = "admissionInfo.applicantName", target = "applicantName"),
            @Mapping(source = "admissionInfo.screening", target = "screening"),
            @Mapping(source = "admissionInfo.schoolName", target = "schoolName"),
            @Mapping(source = "admissionInfo.applicantPhoneNumber", target = "applicantPhoneNumber"),
            @Mapping(source = "admissionInfo.guardianPhoneNumber", target = "guardianPhoneNumber"),
            @Mapping(source = "admissionInfo.teacherPhoneNumber", target = "teacherPhoneNumber"),
            @Mapping(source = "admissionStatus.firstEvaluation", target = "firstEvaluation"),
            @Mapping(source = "admissionStatus.secondEvaluation", target = "secondEvaluation"),
            @Mapping(source = "admissionStatus.registrationNumber", target = "registrationNumber"),
            @Mapping(source = "admissionStatus.secondScore", target = "secondScore"),
    })
    SearchApplicationResDto applicationToSearchApplicationResDto(Application application);

    List<SearchApplicationResDto> applicationsToSearchApplicationResDtos(List<Application> applications);

    default SearchApplicationsResDto applicationsToSearchApplicationsResDto(Page<Application> applications) {
        return new SearchApplicationsResDto(
                new ApplicationListInfoDto(applications.getTotalPages(), applications.getTotalElements()),
                applicationsToSearchApplicationResDtos(applications.getContent())
        );
    }

    default List<List<String>> applicationToExcelDataList(List<Application> applicationList, Boolean sortByFinalScore) {
        List<List<String>> sheetData = new ArrayList<>();
        List<Application> sortedList;

        if (sortByFinalScore) {
            sortedList = applicationList.stream()
                    .sorted(Comparator.comparing(application -> {
                        BigDecimal totalScore = application.getAdmissionGrade().getTotalScore();
                        Optional<BigDecimal> secondScore = application.getAdmissionStatus().getSecondScore();
                        return totalScore.add(secondScore.orElse(BigDecimal.ZERO));
                    }, Comparator.reverseOrder()))
                    .toList();
        } else {
            sortedList = applicationList.stream()
                    .sorted(Comparator.comparing(application ->
                            application.getAdmissionGrade().getTotalScore(),
                            Comparator.reverseOrder())
                    )
                    .toList();
        }

        int index = 1;
        for (Application application : sortedList) {
            AdmissionStatus admissionStatus = application.getAdmissionStatus();
            AdmissionInfo admissionInfo = application.getAdmissionInfo();
            AdmissionGrade admissionGrade = (AdmissionGrade) Hibernate.unproxy(application.getAdmissionGrade());

            BigDecimal finalScore;
            if (admissionStatus.getSecondScore().isEmpty()) {
                finalScore = null;
            } else {
                finalScore = admissionGrade.getTotalScore().add(admissionStatus.getSecondScore().get());
            }

            BigDecimal curricularSubtotalScore;
            BigDecimal articleScore;
            BigDecimal extracurricularSubtotalScore;
            BigDecimal attendanceScore;
            BigDecimal volunteerScore;
            if (admissionInfo.getGraduation().equals(GraduationStatus.GED)) {
                GedAdmissionGrade gedAdmissionGrade = (GedAdmissionGrade) admissionGrade;
                curricularSubtotalScore = gedAdmissionGrade.getGedTotalScore();
                articleScore = null;
                extracurricularSubtotalScore = gedAdmissionGrade.getGedMaxScore();
                attendanceScore = null;
                volunteerScore = null;
            } else {
                GraduateAdmissionGrade graduateAdmissionGrade = (GraduateAdmissionGrade) admissionGrade;
                curricularSubtotalScore = graduateAdmissionGrade.getCurricularSubtotalScore().subtract(graduateAdmissionGrade.getArtisticScore());
                articleScore = graduateAdmissionGrade.getArtisticScore();
                extracurricularSubtotalScore = graduateAdmissionGrade.getExtracurricularSubtotalScore();
                attendanceScore = graduateAdmissionGrade.getAttendanceScore();
                volunteerScore = graduateAdmissionGrade.getVolunteerScore();
            }

            List<String> rowData = List.of(
                    String.valueOf(index),
                    String.valueOf(admissionStatus.getRegistrationNumber().orElse(null)),
                    admissionInfo.getApplicantName(),
                    String.valueOf(admissionInfo.getDesiredMajor().getFirstDesiredMajor()),
                    String.valueOf(admissionInfo.getDesiredMajor().getSecondDesiredMajor()),
                    String.valueOf(admissionInfo.getDesiredMajor().getThirdDesiredMajor()),
                    String.valueOf(admissionInfo.getApplicantBirth()),
                    String.valueOf(admissionInfo.getApplicantGender()),
                    admissionInfo.getAddress(),
                    String.valueOf(admissionInfo.getSchoolName().orElse(null)),
                    String.valueOf(admissionInfo.getGraduation()),
                    String.valueOf(admissionInfo.getScreening()),
                    String.valueOf(admissionStatus.getScreeningFirstEvaluationAt().orElse(null)),
                    String.valueOf(admissionStatus.getScreeningSecondEvaluationAt().orElse(null)),
                    String.valueOf(curricularSubtotalScore),
                    String.valueOf(articleScore),
                    String.valueOf(extracurricularSubtotalScore),
                    String.valueOf(attendanceScore),
                    String.valueOf(volunteerScore),
                    String.valueOf(admissionGrade.getTotalScore()),
                    String.valueOf(admissionStatus.getSecondScore().orElse(null)),
                    String.valueOf(finalScore),
                    admissionInfo.getApplicantPhoneNumber(),
                    admissionInfo.getGuardianPhoneNumber(),
                    String.valueOf(admissionInfo.getTeacherPhoneNumber().orElse(null))
            );

            rowData = rowData.stream()
                    .map(value -> value.equals("null") ? "" : value)
                    .toList();

            sheetData.add(rowData);
            index++;
        }

        return sheetData;
    }
}
