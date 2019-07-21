package coordinator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import coordinator.repositories.MonitorPayloadsRepository;

@Configuration
public class StorageConfiguration {

    @Bean
    public MonitorPayloadsRepository buildMonitorPayloadsRepository(){
        String connectionString = "mongodb://cosmosbolshoi:mkMUpo4YXAGUh6lU7n1s2sWahoCV6OH4QNm9Kl8RUOvJcSp7TAY2bqyf9g8DvjjFNfAnbyj8D9Nu8NGTZPsPwg==@cosmosbolshoi.documents.azure.com:10255/?ssl=true&replicaSet=globaldb";
        String database = "TransferSystem";

        return new MonitorPayloadsRepository(connectionString,database );
    }
}
