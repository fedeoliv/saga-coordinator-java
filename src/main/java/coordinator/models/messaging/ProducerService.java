package coordinator.models.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private Processor processor;

    public <T> ProducerResult send(Message<T> message) {
        ProducerResult result = new ProducerResult();

        try {
            MessageChannel messageChannel = processor.output();
            messageChannel.send(message);
        } catch (RuntimeException e) {
            result.setValid(false);
            result.setErrorMessage("Error while sending message to event stream");
            result.setDetailedErrorMessage(e.getMessage());
        }
        
        return result;
    }

}
