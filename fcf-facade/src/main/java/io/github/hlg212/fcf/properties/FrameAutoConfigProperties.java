package  io.github.hlg212.fcf.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(FrameAutoConfigProperties.PREFIX)
@Data
public class FrameAutoConfigProperties {
    public static final String PREFIX = "fcf.frame.autoconfig";

    private Boolean enable = true;
}
