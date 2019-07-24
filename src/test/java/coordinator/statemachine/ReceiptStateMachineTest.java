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
public class ReceiptStateMachineTest extends StateMachineBaseTest {

    @Test
    public void waitingReceiptState() throws Exception {
        
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.SignalingSucceded,
            Event.BalanceSucceded
        };

        State expectedState = State.WAITING_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void doingReceiptState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.BalanceSucceded,
            Event.SignalingSucceded,
            Event.ReceiptStarted,
        };

        State expectedState = State.DOING_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedReceiptState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.BalanceSucceded,
            Event.SignalingSucceded,
            Event.ReceiptStarted,
            Event.ReceiptSucceded
        };

        State expectedState = State.FINISHED_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
        assertStateMachineIsCompleted();
    }

    @Test
    public void errorReceiptState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.BalanceSucceded,
            Event.SignalingSucceded,
            Event.ReceiptStarted,
            Event.ReceiptError
        };

        State expectedState = State.ERROR_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void startedAdhocReceiptState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.BalanceSucceded,
            Event.SignalingSucceded,
            Event.ReceiptStarted,
            Event.ReceiptError,
            Event.AdhocReceiptRequested
        };

        State expectedState = State.WAITING_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void retryingReceiptState() throws Exception {
        // Arrange
        Event[] incomingEvents = {
                Event.TransferAccepted,
                Event.ValidationStarted,
                Event.ValidationSucceded,
                Event.BalanceStarted,
                Event.SignalingStarted,
                Event.BalanceSucceded,
                Event.SignalingSucceded,
                Event.ReceiptStarted,
                Event.ReceiptError,
                Event.AdhocReceiptRequested,
                Event.ReceiptStarted
            };

        State expectedState = State.DOING_RECEIPT;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }
}
