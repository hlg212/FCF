package  io.github.hlg212.fcf.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fcf.fcf.autoconfig")
@Data
public class FrameAutoConfigProperties {
    private Boolean enable = true;
}
