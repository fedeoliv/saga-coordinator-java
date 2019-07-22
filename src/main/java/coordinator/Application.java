package coordinator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import coordinator.helpers.StateMachineHelper;
import coordinator.models.TransferPayload;
import coordinator.repositories.PayloadRepository;
import coordinator.shared.avro.messages.headers.HeaderConfig;
import coordinator.shared.avro.messages.headers.Headers;
import coordinator.shared.avro.serializers.AvroSerializer;

@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {
    @Autowired
    private StateMachineHelper stateMachineService;

    @Autowired
    private PayloadRepository payloadRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @StreamListener(TransactionStreams.INPUT)
    public void process(Message<byte[]> message) throws Exception {
      try {
        Headers headers = AvroSerializer.deserializeHeaders(message.getPayload());

        String eventType = headers.get(HeaderConfig.EventType).get();
        String transactionId = headers.get(HeaderConfig.TransactionId).get();
        String messageId = headers.get(HeaderConfig.MessageId).get();

        TransferPayload transferPayload = new TransferPayload();
        transferPayload.setMessageId(messageId);
        transferPayload.setTransactionId(transactionId);
        transferPayload.setEventType(eventType);
        transferPayload.setMessageBytes(message.getPayload());
        
        payloadRepository.Add(transferPayload);

        stateMachineService.sendEventForTransaction(transferPayload);
      } catch (Exception e) {
          e.printStackTrace();
          throw e;
      }
      
      commit(message);
    }

    private void commit(Message<byte[]> message) {
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		
		if (acknowledgment != null) {
           System.out.println("Acknowledgment provided");
           acknowledgment.acknowledge();
        }
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Application started!");
        };
    }

}
