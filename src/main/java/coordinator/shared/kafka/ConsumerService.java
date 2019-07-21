package coordinator.shared.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class ConsumerService<K, V> {
    private final KafkaConsumer<K, V> consumer;

    public ConsumerService(String topic, String consumerGroup) {
        consumer = createConsumer(consumerGroup);
        subscribeTo(topic);
    }

    private KafkaConsumer<K, V> createConsumer(String consumerGroup) {
        Properties properties = createKafkaProperties(consumerGroup);
        return new KafkaConsumer<>(properties);
    }

    private void subscribeTo(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    public ConsumerRecords<K, V> poll() {
        return consumer.poll(Duration.ofMillis(1000));
    }

    public void commitAsync() {
        consumer.commitAsync();
    }

    public void close() {
        consumer.close();
    }

    private Properties createKafkaProperties(String consumerGroup) {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafkalabcluster5awnkpc2gu364-vm0.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm1.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm2.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm3.eastus.cloudapp.azure.com:9092");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "PLAIN");
        properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"a0c48ca0d42741fed01aa9d6c032abd9477fcca818e57b53c22fbdeb2df72c43\" user_admin=\"a0c48ca0d42741fed01aa9d6c032abd9477fcca818e57b53c22fbdeb2df72c43\" user_user=\"Mca12345!@#$%\";");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        return properties;
    }
}
