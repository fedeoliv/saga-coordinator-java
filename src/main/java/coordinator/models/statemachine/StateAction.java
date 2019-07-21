package coordinator.models.statemachine;

import org.springframework.statemachine.action.Action;

import coordinator.models.transitions.Events;
import coordinator.models.transitions.States;

public interface StateAction {

    Action<States, Events> transferAcceptedAction();
    Action<States, Events> validationStartedAction();
    Action<States, Events> validationSuccededAction();
    Action<States, Events> balanceStartedAction();
    Action<States, Events> balanceSuccededAction();
    Action<States, Events> signalingStartedAction();
    Action<States, Events> signalingSuccededAction();
    Action<States, Events> receiptStartedAction();
    Action<States, Events> receiptSuccededAction();
    Action<States, Events> transferSuccededAction();
    Action<States, Events> transferFailedAction();

    Action<States, Events> validationFailedAction();
    Action<States, Events> validationErrorAction();
    Action<States, Events> signalingErrorAction();
    Action<States, Events> balanceErrorAction();
    Action<States, Events> receiptErrorAction();
    Action<States, Events> adhocValidationRequestedAction();
    Action<States, Events> adhocSignalingRequestedAction();
    Action<States, Events> adhocReceiptRequestedAction();

    Action<States, Events> undoAllAction();
    Action<States, Events> balanceUndoStartedAction();
    Action<States, Events> balanceUndoFinishedAction();
    Action<States, Events> signalingUndoStartedAction();
    Action<States, Events> signalingUndoFinishedAction();
}
