package coordinator.shared.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;

public class ConsumerServiceMock<K, V> {
    private final String topic;
    private Long offset;
    private final MockConsumer<K, V> consumer;
    private List<ConsumerRecord<K, V>> consumerRecords;

    public ConsumerServiceMock(String topic) {
        this.topic = topic;
        this.offset = 0L;
        this.consumer = createConsumer(topic, new TopicPartition(topic, 0));
        this.consumerRecords = new ArrayList<>();
    }

    private MockConsumer<K, V> createConsumer(String topic, TopicPartition topicPartition) {
        MockConsumer<K, V> consumerMock = new MockConsumer<>(OffsetResetStrategy.NONE);

        consumerMock.assign(Arrays.asList(topicPartition));
        consumerMock.updateBeginningOffsets(createOffsets(topicPartition));

        return consumerMock;
    }

    private HashMap<TopicPartition, Long> createOffsets(TopicPartition topicPartition) {
        HashMap<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(topicPartition, 0L);
        return offsets;
    }

    public ConsumerRecords<K, V> createRecords(LinkedHashMap<K, V> messages) {
        messages.entrySet().forEach(message -> {
            consumerRecords.add(new ConsumerRecord<K, V>(topic, 0, offset++, message.getKey(), message.getValue()));
        });

        HashMap<TopicPartition, List<ConsumerRecord<K, V>>> records = new HashMap<>();
        records.put(new TopicPartition(topic, 0), consumerRecords);
        
        return new ConsumerRecords<K, V>(records);
    }

    public void commitAsync() {
        consumer.commitAsync();
    }
}
