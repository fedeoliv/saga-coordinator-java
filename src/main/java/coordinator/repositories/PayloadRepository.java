
package coordinator.repositories;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;

public class PayloadRepository extends DefaultRepository<String, GenericRecord> {
    @Value("${spring.cosmosdb.collection}")
    private static String collection;

    @Value("${spring.cosmosdb.key}")
    private static String keyName;

    public PayloadRepository(String mongoConnectionString, String database) {
        super(mongoConnectionString, database, collection, keyName);
    }
}
