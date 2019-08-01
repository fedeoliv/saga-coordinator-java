package coordinator.models.messaging;

import lombok.Data;

@Data
public class ProducerResult {
    private boolean valid = true;
    private String errorMessage;
    private String detailedErrorMessage;
}
