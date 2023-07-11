package team.themoment.hellogsm.web.global.logging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request instanceof ContentCachingRequestWrapper cachingRequest) {

            String ip = cachingRequest.getRemoteAddr();
            String uri = cachingRequest.getRequestURI();
            String params = cachingRequest.getQueryString();
            String contentType = cachingRequest.getContentType();

            log.info("[Request] IP: {} | URI: {} | Params: {} | Type: {}", ip, uri, params, contentType);
//            log.debug("[Request] Body: {}", getRequestBody(cachingRequest)); 이유는 모르겠는데,
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (request instanceof ContentCachingRequestWrapper cachingRequest && response instanceof ContentCachingResponseWrapper cachingResponse) {

            String ip = cachingRequest.getRemoteAddr();
            String uri = cachingRequest.getRequestURI();
            String params = cachingRequest.getQueryString();
            String contentType = cachingResponse.getContentType();
            int statusCode = cachingResponse.getStatus();

            log.info("[Response] IP: {} | URI: {} | Params: {} | Type: {} | Status Code: {}", ip, uri, params, contentType, statusCode);
            log.debug("[Request] Body: {}", getRequestBody(cachingRequest));
            log.debug("[Response] Body: {}", getResponseBody(cachingResponse));
        }
    }

    private JsonNode getRequestBody(ContentCachingRequestWrapper request) {
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            byte[] requestBody = request.getContentAsByteArray();
            if (requestBody != null && requestBody.length != 0) {
                try {
                    return objectMapper.readTree(requestBody);
                } catch (IOException e) {
                    log.error("Error reading request body", e);
                }
            }
        }
        return null;
    }

    private JsonNode getResponseBody(ContentCachingResponseWrapper response) {
        if (response.getContentType() != null && response.getContentType().contains("application/json")) {
            byte[] responseBody = response.getContentAsByteArray();
            if (responseBody != null && responseBody.length != 0) {
                try {
                    return objectMapper.readTree(responseBody);
                } catch (IOException e) {
                    log.error("Error reading response body", e);
                }
            }
        }
        return null;
    }
}

