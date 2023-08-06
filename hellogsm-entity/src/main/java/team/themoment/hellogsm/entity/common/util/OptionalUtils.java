package team.themoment.hellogsm.entity.common.util;

import java.util.Optional;

public class OptionalUtils {

    public static <T> T fromOptional(Optional<T> optional) {
        return optional.orElse( null );
    }

}
