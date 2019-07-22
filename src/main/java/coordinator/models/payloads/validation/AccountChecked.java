
package coordinator.models.payloads.validation;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountChecked implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionId;
    private String correlationId;
    private String account;
    private String result;
}
