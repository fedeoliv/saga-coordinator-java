package coordinator.shared.avro.messages.headers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MessageHeader {
    private String id = UUID.randomUUID().toString();
    private String transactionId;
    private String correlationId;
    private String eventType;
    private String source;
    private String creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
    public String getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreationTime() {
        return creationTime;
    }
}
