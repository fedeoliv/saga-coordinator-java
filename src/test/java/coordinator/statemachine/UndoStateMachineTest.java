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
public class UndoStateMachineTest extends StateMachineBaseTest {

    @Test
    public void undoAllRequestedByBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.BalanceError,
            Event.UndoAllRequested,
        };

        State expectedState = State.PARALLEL_TASKS_UNDO;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoAllRequestedBySignalingState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
        };

        State expectedState = State.PARALLEL_TASKS_UNDO;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoAllForkedStatesAfterBalanceTrigger() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.BalanceError,
            Event.UndoAllRequested,
        };

        State expectedState1 = State.WAITING_UNDOING_BALANCE;
        State expectedState2 = State.WAITING_UNDOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState1);
        assertStateMachineIsIn(expectedState2);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoAllForkedStatesAfterSignalingTrigger() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
        };

        State expectedState1 = State.WAITING_UNDOING_BALANCE;
        State expectedState2 = State.WAITING_UNDOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState1);
        assertStateMachineIsIn(expectedState2);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoingBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.BalanceError,
            Event.UndoAllRequested,
            Event.BalanceUndoStarted
        };

        State expectedState = State.UNDOING_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedUndoingBalanceState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.BalanceStarted,
            Event.BalanceError,
            Event.UndoAllRequested,
            Event.BalanceUndoStarted,
            Event.BalanceUndoFinished
        };

        State expectedState = State.FINISHED_UNDOING_BALANCE;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoingSignalingState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
            Event.SignalingUndoStarted
        };

        State expectedState = State.UNDOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void finishedUndoingSignalingState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
            Event.SignalingUndoStarted,
            Event.SignalingUndoSucceded
        };

        State expectedState = State.FINISHED_UNDOING_SIGNALING;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void undoAllFinishedState() throws Exception {

        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.BalanceStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
            Event.SignalingUndoStarted,
            Event.BalanceUndoStarted,
            Event.BalanceUndoFinished,
            Event.SignalingUndoSucceded,
        };

        State expectedState = State.UNDO_ALL_FINISHED;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
    }

    @Test
    public void transferFailedState() throws Exception {
        
        // Arrange
        Event[] incomingEvents = {
            Event.TransferAccepted,
            Event.ValidationStarted,
            Event.ValidationSucceded,
            Event.SignalingStarted,
            Event.BalanceStarted,
            Event.SignalingError,
            Event.UndoAllRequested,
            Event.SignalingUndoStarted,
            Event.BalanceUndoStarted,
            Event.BalanceUndoFinished,
            Event.SignalingUndoSucceded,
            Event.TransferFailed,
        };

        State expectedState = State.TRANSFER_FAILED;

        // Act
        sendToStateMachine(incomingEvents);

        // Assert
        assertStateMachineIsIn(expectedState);
        assertStateMachineAcceptedEveryEvent();
        assertStateMachineIsCompleted();
    }
}    
