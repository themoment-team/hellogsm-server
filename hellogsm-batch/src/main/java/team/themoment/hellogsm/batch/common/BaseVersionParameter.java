package team.themoment.hellogsm.batch.common;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class BaseVersionParameter {
    private final Long version;

    public BaseVersionParameter(@NonNull Long version) {
        Assert.notNull(version, "version은 null일 수 없습니다.");
        this.version = version;
    }
}
