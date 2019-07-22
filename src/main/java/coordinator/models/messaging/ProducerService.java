package coordinator.models.messaging;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import coordinator.TransactionStreams;

@Service
public class ProducerService {
    private final TransactionStreams transactionStreams;

    public ProducerService(TransactionStreams transactionStreams) {
        this.transactionStreams = transactionStreams;
    }

    public ProducerResult send(final byte[] messageBytes) {
        ProducerResult result = new ProducerResult();
        MessageChannel messageChannel = transactionStreams.output();

        try {
            messageChannel.send(MessageBuilder
            .withPayload(messageBytes)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
            .build());
        } catch (RuntimeException e) {
            result.setValid(false);
            result.setErrorMessage("Error while sending message to event stream");
            result.setDetailedErrorMessage(e.getMessage());
        }

        return result;
    }
}
