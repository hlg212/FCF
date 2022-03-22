/**
 * 
 */
package  io.github.hlg212.fcf.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
public class DataSourceCondition implements Condition{

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return "true".equals(context.getEnvironment().getProperty("hlg.enable-data-source"));
	}

}
