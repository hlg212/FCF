package  io.github.hlg212.fcf;


import  io.github.hlg212.fcf.annotation.CloudApplicationScan;
import  io.github.hlg212.fcf.annotation.FrameAutoConfigConditional;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@CloudApplicationScan
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@FrameAutoConfigConditional
public class FrameAutoConfiguration {

}
