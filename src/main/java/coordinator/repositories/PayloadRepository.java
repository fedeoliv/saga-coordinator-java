
package coordinator.repositories;

import org.apache.avro.generic.GenericRecord;

public class PayloadRepository extends DefaultRepository<String, GenericRecord> {
    public PayloadRepository(String mongoConnectionString, String database, String collection, String keyName) {
        super(mongoConnectionString, database, collection, keyName);
    }
}
