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
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

@Configuration
public class StateMachineStorageConfiguration {

	@Value("${redis.host}")
	private String hostName;

	@Value("${redis.port}")
	private int port;
	
	@Value("${redis.password}")
	private String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = createRedisStandaloneConfig();
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().useSsl().build();
		
		return createFactory(configuration, jedisClientConfiguration);
	}

	@Bean
	public StateMachinePersist<State, Event, String> stateMachinePersist(RedisConnectionFactory connectionFactory) {
		
		RedisStateMachineContextRepository<State, Event> repository = new RedisStateMachineContextRepository<State, Event>(connectionFactory);
		
		return new RepositoryStateMachinePersist<State, Event>(repository);
	}

	@Bean
	public RedisStateMachinePersister<State, Event> redisStateMachinePersister(
			StateMachinePersist<State, Event, String> stateMachinePersist) {
		return new RedisStateMachinePersister<State, Event>(stateMachinePersist);
	}

	private RedisStandaloneConfiguration createRedisStandaloneConfig() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
		config.setPassword(password);

		return config;
	}

	private JedisConnectionFactory createFactory(RedisStandaloneConfiguration redisConfig, JedisClientConfiguration jedisConfig) {
		JedisConnectionFactory factory = new JedisConnectionFactory(redisConfig, jedisConfig);
		factory.afterPropertiesSet();

		return factory;
	}
}
