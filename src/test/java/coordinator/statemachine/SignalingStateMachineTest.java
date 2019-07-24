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
public class SignalingStateMachineTest extends StateMachineBaseTest {

    @Test
    public void waitingSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded
        };

        State expectedState = State.WAITING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void doingSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted
        };

        State expectedState = State.DOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingSucceded
        };

        State expectedState = State.FINISHED_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void errorSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError
        };

        State expectedState = State.ERROR_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void startedAdhocSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.AdhocSignalingRequested
        };

        State expectedState = State.WAITING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void retryingSignalingState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.AdhocSignalingRequested,
            Event.SignalingStarted
        };

        State expectedState = State.DOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }
}
