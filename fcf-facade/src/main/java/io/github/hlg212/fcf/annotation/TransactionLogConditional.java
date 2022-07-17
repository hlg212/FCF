package  io.github.hlg212.fcf.annotation;

import io.github.hlg212.fcf.properties.TransactionLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Inherited;


@ConditionalOnProperty(matchIfMissing = true,value = "enable",prefix = TransactionLogProperties.PREFIX)
@Inherited
public @interface TransactionLogConditional {
}
