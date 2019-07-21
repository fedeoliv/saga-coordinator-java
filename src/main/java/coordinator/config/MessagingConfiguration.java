package coordinator.config;

import coordinator.shared.kafka.ProducerService;

// @Configuration
public class MessagingConfiguration {

    public static final String TOPIC = "bolshoi-test";
    public static final String CONSUMER_GROUP = "monitor";

    // @Bean
    public ProducerService<String, byte[]> buildEventProducerService(){
        return new ProducerService<>(TOPIC);
    }
}
