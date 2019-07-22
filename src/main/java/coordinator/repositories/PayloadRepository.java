
package coordinator.repositories;

import org.springframework.beans.factory.annotation.Value;
import coordinator.models.TransferPayload;

public class PayloadRepository extends DefaultRepository<String, TransferPayload> {
    @Value("${cosmosdb.collection}")
    private static String collection;

    @Value("${cosmosdb.key.name}")
    private static String keyName;

    public PayloadRepository(String mongoConnectionString, String database) {
        super(mongoConnectionString, database, collection, keyName);
    }
}
