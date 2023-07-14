package team.themoment.hellogsm.web.global.thirdParty.aws.service.common;

import io.awspring.cloud.s3.S3Exception;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;

@Component
public class AwsExecuteWithExHandle<T> {
    public T handleExceptions(AwsCallBack<T> action) {
        try {
            return action.execute();
        } catch (SdkClientException e) {
            throw new RuntimeException("클라이언트 측(우리) 문제로 인한 예외 발생", e);
        } catch (S3Exception e) {
            throw new RuntimeException("Amazon web service 중 S3에서 예외 발생", e);
        } catch (AwsServiceException e) {
            throw new RuntimeException("Amazon web service에서 예외 발생", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException 발생", e);
        }
    }
}
