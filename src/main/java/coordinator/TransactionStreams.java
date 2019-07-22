package coordinator;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TransactionStreams {
    String INPUT = "mytopic-in";
    String OUTPUT = "mytopic-out";

    @Input(INPUT)
    SubscribableChannel input();
    
    @Output(OUTPUT)
    MessageChannel output();
}
