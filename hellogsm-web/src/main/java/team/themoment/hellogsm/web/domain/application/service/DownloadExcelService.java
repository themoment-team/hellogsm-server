package team.themoment.hellogsm.web.domain.application.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 원서의 정보를 엑셀로 다운로드하는 service interface 입니다.
 */
public interface DownloadExcelService {
    void execute(HttpServletResponse response);
}
