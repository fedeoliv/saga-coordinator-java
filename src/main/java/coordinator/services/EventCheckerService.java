package coordinator.services;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import coordinator.config.MessagingConfiguration;
import coordinator.helpers.StateMachineHelper;
import coordinator.models.MonitorPayload;
import coordinator.repositories.MonitorPayloadsRepository;
import coordinator.shared.avro.messages.headers.HeaderConfig;
import coordinator.shared.avro.messages.headers.Headers;
import coordinator.shared.avro.serializers.AvroSerializer;
import coordinator.shared.kafka.ConsumerService;

@Service
public class EventCheckerService {

    private final ConsumerService<String, byte[]> consumer;

    @Autowired
    private StateMachineHelper stateMachineService;

    @Autowired
	private MonitorPayloadsRepository payloadsRepository;

    public EventCheckerService() {
        consumer = new ConsumerService<>(MessagingConfiguration.TOPIC, MessagingConfiguration.CONSUMER_GROUP);
    }

    @Scheduled(fixedRate = 100) // ms
    public void checkEvents() throws Exception {
        try {
            ConsumerRecords<String, byte[]> consumerRecords = consumer.poll();

            consumerRecords.forEach(record -> {
                try {
                    byte[] messageBytes = record.value();

                    Headers headers = AvroSerializer.deserializeHeaders(messageBytes);

                    String eventType = headers.get(HeaderConfig.EventType).get();
                    String transactionId = headers.get(HeaderConfig.TransactionId).get();
                    String messageId = headers.get(HeaderConfig.MessageId).get();

                    MonitorPayload monitorPayload = new MonitorPayload();
                    monitorPayload.setMessageId(messageId);
                    monitorPayload.setTransactionId(transactionId);
                    monitorPayload.setEventType(eventType);
                    monitorPayload.setMessageBytes(messageBytes);
                    
                    payloadsRepository.Add(monitorPayload);

                    stateMachineService.sendEventForTransaction(monitorPayload);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();

        } catch (CommitFailedException e) {
            System.out.println("CommitFailedException: " + e);
        }
    }

}
