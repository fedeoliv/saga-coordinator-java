package coordinator.models.statemachine;

import org.springframework.statemachine.action.Action;

import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public interface StateAction {

    Action<State, Event> transferAcceptedAction();
    Action<State, Event> validationStartedAction();
    Action<State, Event> validationSuccededAction();
    Action<State, Event> balanceStartedAction();
    Action<State, Event> balanceSuccededAction();
    Action<State, Event> signalingStartedAction();
    Action<State, Event> signalingSuccededAction();
    Action<State, Event> receiptStartedAction();
    Action<State, Event> receiptSuccededAction();
    Action<State, Event> transferSuccededAction();
    Action<State, Event> transferFailedAction();

    Action<State, Event> validationFailedAction();
    Action<State, Event> validationErrorAction();
    Action<State, Event> signalingErrorAction();
    Action<State, Event> balanceErrorAction();
    Action<State, Event> receiptErrorAction();
    Action<State, Event> adhocValidationRequestedAction();
    Action<State, Event> adhocSignalingRequestedAction();
    Action<State, Event> adhocReceiptRequestedAction();

    Action<State, Event> undoAllAction();
    Action<State, Event> balanceUndoStartedAction();
    Action<State, Event> balanceUndoFinishedAction();
    Action<State, Event> signalingUndoStartedAction();
    Action<State, Event> signalingUndoFinishedAction();
}
