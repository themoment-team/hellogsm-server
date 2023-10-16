package team.themoment.hellogsm.web.domain.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.DownloadExcelService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 원서의 정보를 엑셀로 다운로드하는 service implementation 입니다.
 */
@Service
@RequiredArgsConstructor
public class DownloadExcelServiceImpl implements DownloadExcelService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    List<String> headerNameList = List.of(
            "순번",
            "접수번호",
            "성명",
            "1지망",
            "2지망",
            "3지망",
            "생년월일",
            "성별",
            "지역명",
            "출신학교",
            "학력",
            "전형구분",
            "1차합격전형",
            "2차합격전형",
            "일반교과점수",
            "예체능점수",
            "비교과점수",
            "출석점수",
            "봉사점수",
            "전형총점",
            "인적성평가점수",
            "최종점수",
            "지원자연락처",
            "부모연락처",
            "담임연락처"
    );

    /**
     * 일반전형/사회전형/특별전형/불합격 총 4개의 시트를 포함한 엑셀을 생성하여 반환합니다.
     *
     * @Return 엑셀 파일을 반환합니다.
     */
    @Override
    public Workbook execute() {
        Workbook workbook = new SXSSFWorkbook();
        List<Sheet> sheetList = createSheet(workbook);
        List<List<List<String>>> sheetDataList = getSheetDataList();

        for (int i = 0; i < sheetList.size(); i++) {
            int rowCount = 0;

            Sheet sheet = sheetList.get(i);
            Row row = sheet.createRow(rowCount++);

            addDataToRow(row, headerNameList);

            List<List<String>> sheetData = sheetDataList.get(i);

            for (List<String> data : sheetData) {
                row = sheet.createRow(rowCount++);
                addDataToRow(row, data);
            }
        }

        return workbook;
    }

    private List<Sheet> createSheet(Workbook workbook) {
        Sheet generalUserSheet = workbook.createSheet("일반전형");
        Sheet socialUserSheet = workbook.createSheet("사회전형");
        Sheet specialUserSheet = workbook.createSheet("특별전형");
        Sheet failedUsersheet = workbook.createSheet("불합격");
        return List.of(generalUserSheet, socialUserSheet, specialUserSheet, failedUsersheet);
    }

    private List<List<List<String>>> getSheetDataList() {
        List<List<String>> generalSheetData;
        List<List<String>> socialSheetData;
        List<List<String>> specialSheetData;
        List<List<String>> falledSheetData = new ArrayList<>();

        if (!applicationRepository.existsByAdmissionStatusFirstEvaluation(EvaluationStatus.PASS)) {
            generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionInfoScreeningAndAdmissionStatusIsFinalSubmitted(Screening.GENERAL, true), false);
            socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionInfoScreeningAndAdmissionStatusIsFinalSubmitted(Screening.SOCIAL, true), false);
            List<Application> combinedSpecialAppList = Stream.concat(
                            applicationRepository.findAllByAdmissionInfoScreeningAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_VETERANS, true).stream(),
                            applicationRepository.findAllByAdmissionInfoScreeningAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_ADMISSION, true).stream()
                    )
                    .toList();
            specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, false);
        } else {
            if (applicationRepository.existsByAdmissionStatusSecondEvaluation(EvaluationStatus.PASS)) {
                generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAtAndAdmissionStatusSecondEvaluationNotAndAdmissionStatusIsFinalSubmitted(Screening.GENERAL, EvaluationStatus.NOT_YET, true), true);
                socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAtAndAdmissionStatusSecondEvaluationNotAndAdmissionStatusIsFinalSubmitted(Screening.SOCIAL, EvaluationStatus.NOT_YET, true), true);
                List<Application> combinedSpecialAppList = Stream.concat(
                                applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAtAndAdmissionStatusSecondEvaluationNotAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_VETERANS, EvaluationStatus.NOT_YET, true).stream(),
                                applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAtAndAdmissionStatusSecondEvaluationNotAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_ADMISSION, EvaluationStatus.NOT_YET, true).stream()
                        )
                        .toList();
                specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, true);
                falledSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusFirstEvaluationOrAdmissionStatusSecondEvaluationAndAdmissionStatusIsFinalSubmitted(EvaluationStatus.FALL, EvaluationStatus.FALL, true), true);

            } else {
                generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAtAndAdmissionStatusIsFinalSubmitted(Screening.GENERAL, true), false);
                socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAtAndAdmissionStatusIsFinalSubmitted(Screening.SOCIAL, true), false);
                List<Application> combinedSpecialAppList = Stream.concat(
                                applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAtAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_VETERANS, true).stream(),
                                applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAtAndAdmissionStatusIsFinalSubmitted(Screening.SPECIAL_ADMISSION, true).stream()
                        )
                        .toList();
                specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, false);
                falledSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusFirstEvaluationAndAdmissionStatusIsFinalSubmitted(EvaluationStatus.FALL, true), false);
            }
        }

        return List.of(generalSheetData, socialSheetData, specialSheetData, falledSheetData);
    }

    private void addDataToRow(Row row, List<String> data) {
        for (int k = 0; k < headerNameList.size(); k++) {
            Cell cell = row.createCell(k);
            cell.setCellValue(data.get(k));
        }
    }
}
