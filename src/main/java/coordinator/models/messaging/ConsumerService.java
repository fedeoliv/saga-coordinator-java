package coordinator.models.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
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
    public void process(Message<?> message) throws Exception {
        LOGGER.info("Let's process employee details: {}", message.getPayload());

        try {
            payloadRepository.Add(message);

            MessageHeader header = (MessageHeader) message.getHeaders().get(KafkaHeaders.MESSAGE_KEY);
            String transactionId = header.getTransactionId();
            Event event = Event.valueOf(header.getEventType());
            
            stateMachineService.sendEventForTransaction(transactionId, event);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        commit(message);
    }

    private void commit(Message<?> message) {
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		
		if (acknowledgment != null) {
           System.out.println("Acknowledgment provided");
           acknowledgment.acknowledge();
        }
    }
}
