package coordinator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import coordinator.repositories.PayloadRepository;

@Configuration
public class StorageConfiguration {

    @Value("${cosmosdb.connectionString}")
    private String connectionString;

    @Value("${cosmosdb.database}")
    private String database;
    
    @Bean
    public PayloadRepository buildPayloadRepository() {
        return new PayloadRepository(connectionString, database );
    }
} 
