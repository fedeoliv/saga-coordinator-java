package coordinator.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import coordinator.models.TransferPayload;
import coordinator.utils.SpringMessageTools;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public class StateMachineHelper {

    public final String PREFIX = "transactionId:";

    @Autowired
    private StateMachine<State, Event> stateMachine;

    @Autowired
    private StateMachinePersister<State, Event, String> stateMachinePersister;

    public State getStateForTransaction(String transactionId) throws Exception{
        resetStateMachineFromStore(transactionId);
        
        State currentState = stateMachine.getState().getId();

        return currentState;
    }

    public State sendEventForTransaction(TransferPayload transferPayload) throws Exception{
        resetStateMachineFromStore(transferPayload.getTransactionId());

        setExtendedState(SpringMessageTools.transactionId, transferPayload.getTransactionId());

        Event eventTypeEnum = Event.valueOf(transferPayload.getEventType());
        
        String stateBeforeStr = stateMachine.getState().getId().toString();
        
        feedMachine(
            transferPayload.getMessageId(), transferPayload.getTransactionId(),
            transferPayload.getTransactionId(), eventTypeEnum);

        State stateAfter = stateMachine.getState().getId();
        String stateAfterStr = stateAfter.toString();

        System.out.printf("\n#Changed from state %s to state %s\n", stateBeforeStr, stateAfterStr);

        return stateAfter;
    }

    private void feedMachine(
        String messageId, String transactionId, String stateId, Event id) throws Exception {
            
        Message<Event> msg = MessageBuilder
                                    .withPayload(id)
                                    .setHeader(SpringMessageTools.messageId, messageId)
                                    .setHeader(SpringMessageTools.transactionId, transactionId)
                                    .build();
        
        stateMachine.sendEvent(msg);

        persist(stateId);
    }

    private void persist(String stateId) throws Exception{
        stateMachinePersister.persist(stateMachine, PREFIX + stateId);
    }

    private StateMachine<State, Event> resetStateMachineFromStore(String stateId) throws Exception {
        return stateMachinePersister.restore(stateMachine, PREFIX + stateId);
    }

	public void setExtendedState(String transactionid, String transactionIdValue) {
        stateMachine.getExtendedState().getVariables().put(SpringMessageTools.transactionId, transactionIdValue);
	}
}
