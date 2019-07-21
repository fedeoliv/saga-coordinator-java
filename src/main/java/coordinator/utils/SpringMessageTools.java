package coordinator.utils;

import org.springframework.statemachine.StateContext;

import coordinator.models.Events;
import coordinator.models.States;

public class SpringMessageTools {

    public static final String messageId = "messageId";
    public static final String transactionId = "transactionId";
    public static final String correlationId = "correlationId";

    public static String extractMessageId(StateContext<States, Events> context) {
        return getHeader(context, SpringMessageTools.messageId);
    }

	public static String extractTransactionId(StateContext<States, Events> context) {
		return getHeader(context, SpringMessageTools.transactionId);
	}

	public static String extractCorrelationId(StateContext<States, Events> context) {
		return getHeader(context, SpringMessageTools.correlationId);
    }
    
    private static String getHeader(StateContext<States, Events> context, String header){
        return (String) context.getMessage().getHeaders().get(header);
    }

	public static String extractTransactionIdFromExtendedMachine(StateContext<States, Events> context) {
		return context.getExtendedState().get(SpringMessageTools.transactionId, String.class);
    }

}
