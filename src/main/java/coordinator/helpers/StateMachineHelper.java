package coordinator.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import coordinator.models.TransferPayload;
import coordinator.utils.SpringMessageTools;
import coordinator.models.transitions.Events;
import coordinator.models.transitions.States;

public class StateMachineHelper {

    public final String PREFIX = "transactionId:";

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private StateMachinePersister<States, Events, String> stateMachinePersister;

    public States getStateForTransaction(String transactionId) throws Exception{
        resetStateMachineFromStore(transactionId);
        
        States currentState = stateMachine.getState().getId();

        return currentState;
    }

    public States sendEventForTransaction(TransferPayload transferPayload) throws Exception{
        resetStateMachineFromStore(transferPayload.getTransactionId());

        setExtendedState(SpringMessageTools.transactionId, transferPayload.getTransactionId());

        Events eventTypeEnum = Events.valueOf(transferPayload.getEventType());
        
        String stateBeforeStr = stateMachine.getState().getId().toString();
        
        feedMachine(
            transferPayload.getMessageId(), transferPayload.getTransactionId(),
            transferPayload.getTransactionId(), eventTypeEnum);

        States stateAfter = stateMachine.getState().getId();
        String stateAfterStr = stateAfter.toString();

        System.out.printf("\n#Changed from state %s to state %s\n", stateBeforeStr, stateAfterStr);

        return stateAfter;
    }

    private void feedMachine(
        String messageId, String transactionId, String stateId, Events id) throws Exception {
            
        Message<Events> msg = MessageBuilder
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

    private StateMachine<States, Events> resetStateMachineFromStore(String stateId) throws Exception {
        return stateMachinePersister.restore(stateMachine, PREFIX + stateId);
    }

	public void setExtendedState(String transactionid, String transactionIdValue) {
        stateMachine.getExtendedState().getVariables().put(SpringMessageTools.transactionId, transactionIdValue);
	}
}
