package  io.github.hlg212.fcf.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(matchIfMissing = true,value = "log.sql.enable",prefix = "hlg")
@Inherited
public @interface SqlLogConditional {
}
