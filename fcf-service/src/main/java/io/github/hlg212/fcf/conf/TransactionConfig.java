package  io.github.hlg212.fcf.conf;

import  io.github.hlg212.fcf.Constants;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(Constants.FRAME_BEAN_PREFIX + "TransactionConfig")
public class TransactionConfig {


//	@Bean
//	public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
//		return new RabbitTransactionManager(connectionFactory);
//	}
}
