package com.hlg.fcf;


import com.hlg.fcf.annotation.CloudApplicationScan;
import com.hlg.fcf.annotation.FrameAutoConfigConditional;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@CloudApplicationScan
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@FrameAutoConfigConditional
public class FrameAutoConfiguration {

}
