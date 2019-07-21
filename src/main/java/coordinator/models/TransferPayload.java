package coordinator.models;

public class TransferPayload {

    private String messageId;
    private String transactionId;
    private String eventType;
    private byte[] messageBytes;

    /**
     * @return the transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the messageBytes
     */
    public byte[] getMessageBytes() {
        return messageBytes;
    }

    /**
     * @param messageBytes the messageBytes to set
     */
    public void setMessageBytes(byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
