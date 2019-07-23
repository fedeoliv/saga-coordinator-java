package coordinator.utils;

import org.springframework.statemachine.StateContext;

import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public class SpringMessageTools {

    public static final String messageId = "messageId";
    public static final String transactionId = "transactionId";
    public static final String correlationId = "correlationId";

    public static String extractMessageId(StateContext<State, Event> context) {
        return getHeader(context, SpringMessageTools.messageId);
    }

	public static String extractTransactionId(StateContext<State, Event> context) {
		return getHeader(context, SpringMessageTools.transactionId);
	}

	public static String extractCorrelationId(StateContext<State, Event> context) {
		return getHeader(context, SpringMessageTools.correlationId);
    }
    
    private static String getHeader(StateContext<State, Event> context, String header){
        return (String) context.getMessage().getHeaders().get(header);
    }

	public static String extractTransactionIdFromExtendedMachine(StateContext<State, Event> context) {
		return context.getExtendedState().get(SpringMessageTools.transactionId, String.class);
    }

}
