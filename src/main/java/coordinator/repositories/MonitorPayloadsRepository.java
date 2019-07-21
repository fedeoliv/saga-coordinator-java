
package coordinator.repositories;

import coordinator.models.MonitorPayload;

public class MonitorPayloadsRepository extends DefaultRepository<String, MonitorPayload> {

    private static final String COLLECTION = "monitorPayloads";
    private static final String KEYNAME = "messageId";

    public MonitorPayloadsRepository(String mongoConnectionString, String database) {
        super(mongoConnectionString, database, COLLECTION, KEYNAME);
    }
}
