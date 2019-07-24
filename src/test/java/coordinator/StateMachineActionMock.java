package coordinator;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import coordinator.models.statemachine.StateAction;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public class StateMachineActionMock implements StateAction {

    @Override
    public Action<State, Event> transferAcceptedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> validationStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> validationSuccededAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> balanceStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> balanceSuccededAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> signalingStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> signalingSuccededAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> receiptStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> receiptSuccededAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> transferSuccededAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> transferFailedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> validationErrorAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> signalingErrorAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> balanceErrorAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> adhocValidationRequestedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> adhocSignalingRequestedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> undoAllAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> balanceUndoStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> balanceUndoFinishedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> signalingUndoStartedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> signalingUndoFinishedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {

            }
        };
    }

    @Override
    public Action<State, Event> receiptErrorAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> adhocReceiptRequestedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }

    @Override
    public Action<State, Event> validationFailedAction() {
        return new Action<State, Event>() {
            @Override
            public void execute(StateContext<State, Event> context) {
            }
        };
    }
}
