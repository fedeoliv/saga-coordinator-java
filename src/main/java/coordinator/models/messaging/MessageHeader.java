package coordinator.models.messaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageHeader {
    private String id = UUID.randomUUID().toString();
    private String transactionId;
    private String correlationId;
    private String eventType;
    private String source;
    private String creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
}
