package io.hlg212.fcf;


import io.hlg212.fcf.annotation.CloudApplicationScan;
import io.hlg212.fcf.annotation.FrameAutoConfigConditional;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@CloudApplicationScan
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@FrameAutoConfigConditional
public class FrameAutoConfiguration {

}
