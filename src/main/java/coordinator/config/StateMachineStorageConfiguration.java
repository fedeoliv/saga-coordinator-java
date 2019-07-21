package coordinator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.data.redis.RedisStateMachinePersister;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import coordinator.models.transitions.Events;
import coordinator.models.transitions.States;

@Configuration
public class StateMachineStorageConfiguration {

	@Value("${spring.redis.host}")
	private String REDIS_HOSTNAME ;

	@Value("${spring.redis.port}")
	private int REDIS_PORT;
	
	@Value("${spring.redis.password}")
	private String REDIS_PASS;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);
		configuration.setPassword(REDIS_PASS);
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().useSsl().build();
		JedisConnectionFactory factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
		factory.afterPropertiesSet();

		return factory;
	}

	@Bean
	public StateMachinePersist<States, Events, String> stateMachinePersist(RedisConnectionFactory connectionFactory) {
		
		RedisStateMachineContextRepository<States, Events> repository = new RedisStateMachineContextRepository<States, Events>(connectionFactory);
		
		return new RepositoryStateMachinePersist<States, Events>(repository);
	}

	@Bean
	public RedisStateMachinePersister<States, Events> redisStateMachinePersister(
			StateMachinePersist<States, Events, String> stateMachinePersist) {
		return new RedisStateMachinePersister<States, Events>(stateMachinePersist);
	}
}
