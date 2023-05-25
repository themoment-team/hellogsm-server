package team.themoment.hellogsm.web.global.exception.model;

/**
 *  Exception 발생 시 클라이언트에 반환할 Response 객체입니다
 *
 *  @author 양시준
 *  @since 1.0.0
 */
public record ExceptionResponseEntity(String message) {

    /**
     * Throwable 객체를 입력으로 받아 ExceptionResponseEntity 객체를 생성합니다.
     *
     * @param exception {@code Throwable} 객체
     * @return 예외 응답 객체
     */
    public static ExceptionResponseEntity of(final Throwable exception) {
        return new ExceptionResponseEntity(exception.getMessage());
    }

}
