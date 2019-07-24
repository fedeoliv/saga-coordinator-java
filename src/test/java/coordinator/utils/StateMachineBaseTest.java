package coordinator.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.springframework.statemachine.StateMachine;
import coordinator.StateMachineActionMock;
import coordinator.models.statemachine.StateMachineFactory;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;
import junit.framework.AssertionFailedError;

public abstract class StateMachineBaseTest {

    private StateMachineFactory stateMachineFactory;
    private StateMachine<State, Event> stateMachine;
    private StateMachineSender sender;

    public StateMachineBaseTest() {
        StateMachineActionMock actionMock = new StateMachineActionMock();
        stateMachineFactory = new StateMachineFactory(actionMock);
    }

    @Before
    public void beforeTests() {
        try {
            stateMachine = stateMachineFactory.buildStateMachine();
            sender = new StateMachineSender(stateMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToStateMachine(Event[] events) throws Exception {
        sender.sendEvents(events);
    }

    public void assertStateMachineAcceptedEveryEvent() {
        HashMap<Event, Boolean> eventResults = sender.getEventResults();

        for (Map.Entry<Event, Boolean> e : eventResults.entrySet()) {
            Event event = e.getKey();
            Boolean result = e.getValue();

            if (!result) {
                throw new AssertionFailedError(String.format("Event %s was not accepted by the state machine", event));
            }
        }
    }

    public void assertStateMachineIsIn(State expectedState) {
        Collection<State> currentStates = stateMachine.getState().getIds();
        assertThat(currentStates, hasItem(expectedState));
    }

    public void assertStateMachineIsCompleted() {
        assertEquals(true, stateMachine.isComplete());
    }
}
