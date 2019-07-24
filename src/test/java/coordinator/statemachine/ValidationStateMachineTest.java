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
public class ValidationStateMachineTest extends StateMachineBaseTest {

    @Test
    public void waitingValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted
        };

        State expectedState = State.WAITING_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void doingValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted
        };

        State expectedState = State.DOING_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = { 
            Event.TransferAccepted, 
            Event.ValidationStarted, 
            Event.ValidationSucceded 
        };

        State expectedState = State.PARALLEL_TASKS;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void errorValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationError
        };

        State expectedState = State.ERROR_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void startedAdhocValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationError,
            Event.AdhocValidationRequested
        };

        State expectedState = State.WAITING_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void retryingValidationState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationError,
            Event.AdhocValidationRequested,
            Event.ValidationStarted
        };

        State expectedState = State.DOING_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void failedValidationState() throws Exception {
        
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationFailed
        };

        State expectedState = State.FAILED_VALIDATION;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
        assertStateMachineIsCompleted();
    }
}
