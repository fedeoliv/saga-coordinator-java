
package coordinator.repositories;

import coordinator.models.TransferPayload;

public class PayloadRepository extends DefaultRepository<String, TransferPayload> {

    private static final String COLLECTION = "monitorPayloads";
    private static final String KEYNAME = "messageId";

    public PayloadRepository(String mongoConnectionString, String database) {
        super(mongoConnectionString, database, COLLECTION, KEYNAME);
    }
}
