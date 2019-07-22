
package coordinator.models.payloads.receipt;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceiptError implements Serializable {

    private static final long serialVersionUID = 1L;
    private String transactionId;
    private String correlationId;
    private String message;
}
