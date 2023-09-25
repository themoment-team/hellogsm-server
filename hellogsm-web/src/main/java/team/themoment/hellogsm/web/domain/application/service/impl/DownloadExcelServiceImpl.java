package team.themoment.hellogsm.web.domain.application.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import team.themoment.hellogsm.entity.domain.application.entity.Application;
import team.themoment.hellogsm.entity.domain.application.enums.EvaluationStatus;
import team.themoment.hellogsm.entity.domain.application.enums.Screening;
import team.themoment.hellogsm.web.domain.application.mapper.ApplicationMapper;
import team.themoment.hellogsm.web.domain.application.repository.ApplicationRepository;
import team.themoment.hellogsm.web.domain.application.service.DownloadExcelService;

import java.io.IOException;
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

    Workbook workbook = new XSSFWorkbook();
    Sheet generalUserSheet = workbook.createSheet("일반전형");
    Sheet socialUserSheet = workbook.createSheet("사회전형");
    Sheet specialUserSheet = workbook.createSheet("특별전형");
    Sheet failedUsersheet = workbook.createSheet("불합격");
    List<Sheet> sheetList = List.of(generalUserSheet, socialUserSheet, specialUserSheet, failedUsersheet);

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
     * @param response (HttpServletResponse) 엑셀을 담는 용도
     */
    @Override
    public void execute(HttpServletResponse response) {
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


        response.setContentType("applicaton/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");

        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException("파일 작성과정에서 예외가 발생하였습니다.");
        }
    }

    private List<List<List<String>>> getSheetDataList() {
        List<List<String>> generalSheetData;
        List<List<String>> socialSheetData;
        List<List<String>> specialSheetData;
        List<List<String>> falledSheetData = new ArrayList<>();

        if (applicationRepository.existsByAdmissionStatusFirstEvaluation(EvaluationStatus.NOT_YET)) {
            generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionInfoScreening(Screening.GENERAL), false);
            socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionInfoScreening(Screening.SOCIAL), false);
            List<Application> combinedSpecialAppList = Stream.concat(
                            applicationRepository.findAllByAdmissionInfoScreening(Screening.SPECIAL_VETERANS).stream(),
                            applicationRepository.findAllByAdmissionInfoScreening(Screening.SPECIAL_ADMISSION).stream()
                    )
                    .toList();
            specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, false);
        } else {
            if (applicationRepository.existsByAdmissionStatusSecondEvaluation(EvaluationStatus.NOT_YET)) {
                generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAt(Screening.GENERAL), false);
                socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAt(Screening.SOCIAL), false);
                List<Application> combinedSpecialAppList = Stream.concat(
                                applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAt(Screening.SPECIAL_VETERANS).stream(),
                                applicationRepository.findAllByAdmissionStatusScreeningFirstEvaluationAt(Screening.SPECIAL_ADMISSION).stream()
                        )
                        .toList();
                specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, false);
                falledSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatus_FirstEvaluation(EvaluationStatus.FALL), false);

            } else {
                generalSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAt(Screening.GENERAL), true);
                socialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAt(Screening.SOCIAL), true);
                List<Application> combinedSpecialAppList = Stream.concat(
                                applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAt(Screening.SPECIAL_VETERANS).stream(),
                                applicationRepository.findAllByAdmissionStatusScreeningSecondEvaluationAt(Screening.SPECIAL_ADMISSION).stream()
                        )
                        .toList();
                specialSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(combinedSpecialAppList, true);
                falledSheetData = applicationMapper.INSTANCE.applicationToExcelDataList(applicationRepository.findAllByAdmissionStatusSecondEvaluation(EvaluationStatus.FALL), true);
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
