package team.themoment.hellogsm.web.global.aop.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Slf4j
@Aspect
@Component
public class HttpLoggingAspect {
    //TODO HttpLogging은 Filter or Interceptor 계층에서 진행하도록 변경
    // 현재 방식은 로깅에 제한이 있기도 하고, Filter 단에서 인증,인가 등의 이유로 요청이 block 된 경우 확인 불가능

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void onRequest() {
    }


    @Around("onRequest()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String sessionId = request.getRequestedSessionId();
        String params = request.getQueryString();
        String contentType = request.getContentType();
        String userAgent = request.getHeader("User-Agent");


        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        Enumeration<String> headerNames = request.getHeaderNames();
        Set<String> headerSet = new HashSet<>();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerSet.add(headerName);
        }

        UUID code = UUID.randomUUID(); // 나중에 찾기 쉽게 Req/Res가 한 세트인 id 느낌

        log.info("At {}#{} [Request:{}] IP: {}, Session-ID: {}, URI: {}, Params: {}, Content-Type: {}, User-Agent: {}, Headers: {}, Parameters: {}, Code: {}",
                className, methodName, method, ip, sessionId, uri, params, contentType, userAgent, headerSet, params(proceedingJoinPoint), code);

        Object result = proceedingJoinPoint.proceed();

        if (result instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) result;
            log.info("At {}#{} [Response:{}] IP: {}, Session-ID: {}, Headers: {}, Response: {}, Status-Code: {}, Code: {}",
                    className, methodName, method, ip, sessionId, responseEntity.getHeaders(), responseEntity.getBody(), responseEntity.getStatusCode(), code);
        } else if (result == null) {
            log.info("At {}#{} [Response: null] IP: {}, Session-ID: {}, Code: {}",
                    className, methodName, ip, sessionId, code);
        } else {
            throw new RuntimeException("유효하지 않은 Controller 반환 타입");
        }

        return result;
    }
    private Map params(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }
}
