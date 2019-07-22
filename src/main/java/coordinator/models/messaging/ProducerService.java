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

    public void send(final byte[] messageBytes) {
        MessageChannel messageChannel = transactionStreams.output();

        messageChannel.send(MessageBuilder
            .withPayload(messageBytes)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
            .build());
    }
}
