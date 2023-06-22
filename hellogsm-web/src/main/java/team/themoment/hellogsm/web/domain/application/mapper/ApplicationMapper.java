package team.themoment.hellogsm.web.domain.application.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GraduateAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.entity.domain.application.enums.GraduationStatus;
import team.themoment.hellogsm.web.domain.application.dto.domain.*;
import team.themoment.hellogsm.web.domain.application.dto.response.SingleApplicationRes;
import team.themoment.hellogsm.web.domain.application.dto.response.TicketResDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.WARN
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
            @Mapping(source = "registrationNumber", target = "registrationNumber"),
            @Mapping(source = "secondScore", target = "secondScore"),
            @Mapping(source = "finalMajor", target = "finalMajor"),
    })
    AdmissionStatusDto admissionStatusToAdmissionStatusDto(AdmissionStatus admissionStatus);

    @BeanMapping(ignoreUnmappedSourceProperties = {"middleSchoolGrade", "admissionGrade", "userId"})
    @Mappings({
            @Mapping(source = "id", target = "applicationId"),
            @Mapping(source = "admissionInfo.applicantName", target = "applicantName"),
            @Mapping(source = "admissionInfo.applicantGender", target = "applicantGender"),
            @Mapping(source = "admissionInfo.applicantBirth", target = "applicantBirth"),
            @Mapping(source = "admissionInfo.applicantImageUri", target = "applicantImageUri"),
            @Mapping(source = "admissionInfo.address", target = "address"),
            @Mapping(source = "admissionInfo.graduation", target = "graduation"),
            @Mapping(source = "admissionStatus.registrationNumber", target = "registrationNumber"),
    })
    TicketResDto ApplicationToTicketResDto(Application application);

    List<TicketResDto> ApplicationListToTicketResDtoList(List<Application> applicationList);
}
