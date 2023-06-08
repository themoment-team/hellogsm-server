package team.themoment.hellogsm.web.domain.application.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import team.themoment.hellogsm.entity.domain.application.entity.admission.AdmissionInfo;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GedAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.grade.GraduateAdmissionGrade;
import team.themoment.hellogsm.entity.domain.application.entity.status.AdmissionStatus;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionInfoDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.AdmissionStatusDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GedAdmissionGradeDto;
import team.themoment.hellogsm.web.domain.application.dto.domain.GeneralAdmissionGradeDto;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.WARN
)
public interface ApplicationMapper {
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

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
}
