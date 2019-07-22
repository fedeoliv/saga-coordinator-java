package coordinator.models.messaging;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProducerResult {
    private boolean valid = true;
    private String errorMessage;
    private String detailedErrorMessage;
}
