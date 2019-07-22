
package coordinator.models.payloads.balance;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BalanceStarted implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionId;
    private String correlationId;
    private String accountFromId;
    private String accountToId;
    private Double amount;
}
