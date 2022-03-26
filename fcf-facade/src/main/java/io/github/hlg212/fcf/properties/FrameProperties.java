package  io.github.hlg212.fcf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "hlg212.fcf")
@Data
@Component
public class FrameProperties {
	
	private String version ;

	private String basePackage ;

	private String web;

	private String dao;

	private String service;


}
