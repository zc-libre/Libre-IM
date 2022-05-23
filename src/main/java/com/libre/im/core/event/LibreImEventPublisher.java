package com.libre.im.core.event;

import com.libre.boot.autoconfigure.SpringContext;
import com.libre.im.core.exception.LibreImException;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

/**
 * @author: Libre
 * @Date: 2022/5/2 10:09 PM
 */
public final class LibreImEventPublisher {

    private static final ApplicationContext applicationContext;

    static {
        applicationContext = Optional.ofNullable(SpringContext.getContext())
                .orElseThrow(() -> new LibreImException("applicationContext is null"));
    }

    public static void publishSaveMessageEvent() {

    }

}
