package coordinator.models.messaging;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import coordinator.helpers.StateMachineHelper;
import coordinator.models.transitions.Event;
import coordinator.repositories.PayloadRepository;

@Service
public class ConsumerService {

    @Autowired
    private StateMachineHelper stateMachineService;
    
    @Autowired
    private PayloadRepository payloadRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @StreamListener(Processor.INPUT)
    public void process(
        @Payload GenericRecord payload,
        @Headers MessageHeader header,
        @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment ack) {
        
        LOGGER.info("Payload: {}", payload);
        
        try {
            payloadRepository.add(payload);

            String transactionId = header.getTransactionId();
            Event event = Event.valueOf(header.getEventType());
            
            stateMachineService.sendEventForTransaction(transactionId, event);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }
}
