package coordinator.statemachine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;
import coordinator.utils.StateMachineBaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceStateMachineTest extends StateMachineBaseTest {

    @Test
    public void waitingBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = { 
            Event.TransferAccepted, 
            Event.ValidationStarted, 
            Event.ValidationSucceded 
        };

        State expectedState = State.WAITING_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void doingBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = { 
            Event.TransferAccepted, 
            Event.ValidationStarted, 
            Event.ValidationSucceded,
            Event.BalanceStarted 
        };

        State expectedState = State.DOING_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedBalanceState() throws Exception {
        
        // Arrange
        Event[] incomingEvents = { 
            Event.TransferAccepted, 
            Event.ValidationStarted, 
            Event.ValidationSucceded, 
            Event.BalanceStarted, 
            Event.BalanceSucceded 
        };

        State expectedState = State.FINISHED_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void errorBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = { 
            Event.TransferAccepted, 
            Event.ValidationStarted, 
            Event.ValidationSucceded,
            Event.BalanceStarted, 
            Event.BalanceError 
        };

        State expectedState = State.ERROR_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }
}
