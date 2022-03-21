package io.hlg212.fcf.core.cache;

import lombok.Data;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@Data
public class CusRedisCacheConfiguration {

    private String name;

    private RedisCacheConfiguration redisCacheConfiguration;
}
