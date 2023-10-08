package team.themoment.hellogsm.web.global.exception.error;


import org.springframework.http.HttpStatus;

/**
 * 클라이언트에게 HttpStatus와 message를 전달하기 위해 사용되는 Exception입니다. <br>
 * 클라이언트의 입력 값이 유효하지 않거나, 특정 조건을 만족하지 않았을 때 등 다양한 상황에서 사용됩니다.
 *
 * <p>
 * 이 Exception은 Stack Trace를 생성하지 않으므로, 다른 Exception을 받아서 re-throw하지 말아야 합니다.
 *
 * <p>
 * 사용 예시
 *  <pre>
 *  {@code
 *  throw new ExpectedException("any message", HttpStatus.FORBIDDEN);
 *  throw new ExpectedException(HttpStatus.FORBIDDEN);
 *  }
 *  </pre>
 *
 *  @see HttpStatus
 */
public class ExpectedException extends RuntimeException {

    private final HttpStatus statusCode;

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public ExpectedException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * HttpStatus만 입력 받아 ExpectedException 객체를 생성합니다.
     * 예외 메시지는 HttpStatus의 ReasonPhrase를 사용합니다.
     *
     * @param statusCode HTTP 상태 코드
     */
    public ExpectedException(HttpStatus statusCode) {
        this(statusCode.getReasonPhrase(), statusCode);
    }

    /**
     * Stack Trace를 생성하지 않도록 fillInStackTrace 메서드를 Override합니다.
     *
     * @return 현재 예외 객체
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
