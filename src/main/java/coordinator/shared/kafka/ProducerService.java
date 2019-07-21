package coordinator.shared.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerService<K, V> {
    private final String topic;
    private final KafkaProducer<K, V> producer;

    public ProducerService(String topic) {
        this.topic = topic;
        producer = createProducer();
    }

    public void send(K key, V value) {
        ProducerRecord<K, V> record = new ProducerRecord<>(topic, key, value);

        producer.send(record);
        producer.flush();
        producer.close();
    }

    private KafkaProducer<K, V> createProducer() {
        Properties properties = createKafkaProperties();
        return new KafkaProducer<>(properties);
    }

    private Properties createKafkaProperties() {
        Properties properties = new Properties();
        
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafkalabcluster5awnkpc2gu364-vm0.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm1.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm2.eastus.cloudapp.azure.com:9092,kafkalabcluster5awnkpc2gu364-vm3.eastus.cloudapp.azure.com:9092");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "PLAIN");
        properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"a0c48ca0d42741fed01aa9d6c032abd9477fcca818e57b53c22fbdeb2df72c43\" user_admin=\"a0c48ca0d42741fed01aa9d6c032abd9477fcca818e57b53c22fbdeb2df72c43\" user_user=\"Mca12345!@#$%\";");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);

        return properties;

    }
}
