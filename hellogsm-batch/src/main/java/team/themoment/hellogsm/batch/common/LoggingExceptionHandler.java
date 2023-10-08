package team.themoment.hellogsm.batch.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;

@Slf4j
public class LoggingExceptionHandler implements ExceptionHandler {
    @Override
    public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
        log.error("Error Occur : {}", throwable.toString());
        throw throwable;
    }
}
