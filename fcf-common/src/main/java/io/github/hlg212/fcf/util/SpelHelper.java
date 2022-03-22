package  io.github.hlg212.fcf.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * spel
 *
 * @author  huangligui
 * @create: 2019-03-06 09:44
 **/
public class SpelHelper {


    public static Object parseExpression(String spel,Object ...objs)
    {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();

        if( objs != null ) {
            for (int i = 0; i < objs.length; i++) {
                context.setVariable("p" + i, objs[i]);
            }
        }
        return parser.parseExpression(spel).getValue(context);
    }
}
