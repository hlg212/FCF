package  io.github.hlg212.fcf.annotation;

import io.github.hlg212.fcf.properties.FrameAutoConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(matchIfMissing = true,value = "enable",prefix = FrameAutoConfigProperties.PREFIX)
@Inherited
public @interface FrameAutoConfigConditional {
}
