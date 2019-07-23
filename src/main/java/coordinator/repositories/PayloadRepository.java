
package coordinator.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;

public class PayloadRepository extends DefaultRepository<String, Message<?>> {
    @Value("${spring.cosmosdb.collection}")
    private static String collection;

    @Value("${spring.cosmosdb.key}")
    private static String keyName;

    public PayloadRepository(String mongoConnectionString, String database) {
        super(mongoConnectionString, database, collection, keyName);
    }
}
