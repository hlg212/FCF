package  io.github.hlg212.fcf.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 *
 * @author huangligui
 * @date 2018年12月24日
 */
@Data
public class ExRedisProperties extends RedisProperties {

    public ExRedisProperties()
    {
        setHost(null);
        setPort(0);
    }
}
