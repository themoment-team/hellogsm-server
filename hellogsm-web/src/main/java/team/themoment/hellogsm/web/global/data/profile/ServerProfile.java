package team.themoment.hellogsm.web.global.data.profile;

public class ServerProfile {

    public static final String LOCAL = "local";
    public static final String PROD = "prod";

    private ServerProfile() {
        throw new IllegalStateException("인스턴스 생성이 불가능한 클래스입니다");
    }
}
