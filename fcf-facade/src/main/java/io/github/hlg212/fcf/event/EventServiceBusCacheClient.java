package  io.github.hlg212.fcf.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author  huangligui
 * @create: 2019-03-06 14:00
 **/
public interface EventServiceBusCacheClient {
    String INPUT = "eventServiceBusCacheInput";

    @Input(INPUT)
    MessageChannel eventServiceBusCacheInput();
}
