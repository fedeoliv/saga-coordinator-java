package coordinator.shared.kafka;

import java.util.List;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerServiceMock<K, V> {
    private final String topic;
    private final MockProducer<K, V> producer;

    public ProducerServiceMock(String topic) {
        this.topic = topic;
        producer = new MockProducer<K, V>();
    }

    public void send(K key, V value) {
        ProducerRecord<K, V> record = new ProducerRecord<>(topic, key, value);
        producer.send(record);
    }

    public List<ProducerRecord<K, V>> getHistory() {
        return producer.history();
    }
}
