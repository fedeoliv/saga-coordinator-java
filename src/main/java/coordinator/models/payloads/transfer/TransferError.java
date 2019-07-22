
package coordinator.models.payloads.transfer;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransferError  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionId;
    private String correlationId;
    private String message;
}
