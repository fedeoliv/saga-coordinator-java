package coordinator.models.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import coordinator.models.statemachine.StateAction;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

public class StateMachineFactory {

    private StateAction stateMachineAction;

    public StateMachineFactory(StateAction stateMachineActions) {
        this.stateMachineAction = stateMachineActions;
    }

    public StateMachine<State, Event> buildStateMachine() throws Exception {
        Builder<State, Event> builder = StateMachineBuilder.builder();

        configure(builder.configureStates());
        configure(builder.configureTransitions());

        return builder.build();
    }

    private void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
            .withStates()
                .initial(State.MONITORING_NOT_STARTED)
                .state(State.WAITING_VALIDATION)
                .state(State.DOING_VALIDATION)
                .state(State.ERROR_VALIDATION)
                .state(State.PARALLEL_TASKS)
                .state(State.WAITING_RECEIPT)
                .state(State.DOING_RECEIPT)
                .state(State.ERROR_RECEIPT)
                .state(State.PARALLEL_TASKS_UNDO)
                .state(State.UNDO_ALL_FINISHED)
                .fork(State.FINISHED_VALIDATION)
                .join(State.JOIN_PARALLEL_TASKS)
                .fork(State.UNDO_ALL)
                .join(State.JOIN_UNDO)
                .end(State.FINISHED_RECEIPT)
                .end(State.FAILED_VALIDATION)
                .end(State.TRANSFER_FAILED)
                .and().withStates()
                    .parent(State.PARALLEL_TASKS)
                    .initial(State.WAITING_SIGNALING)
                    .state(State.DOING_SIGNALING)
                    .state(State.ERROR_SIGNALING)
                    .end(State.FINISHED_SIGNALING)
                .and().withStates()
                    .parent(State.PARALLEL_TASKS)
                    .initial(State.WAITING_BALANCE)
                    .state(State.DOING_BALANCE)
                    .state(State.ERROR_BALANCE)
                    .end(State.FINISHED_BALANCE)
                .and().withStates()
                    .parent(State.PARALLEL_TASKS_UNDO)
                    .initial(State.WAITING_UNDOING_BALANCE)
                    .state(State.UNDOING_BALANCE)
                    .end(State.FINISHED_UNDOING_BALANCE)
                .and().withStates()
                    .parent(State.PARALLEL_TASKS_UNDO)
                    .initial(State.WAITING_UNDOING_SIGNALING)
                    .state(State.UNDOING_SIGNALING)
                    .end(State.FINISHED_UNDOING_SIGNALING);
    }

    private void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
		transitions
			.withExternal()
				.source(State.MONITORING_NOT_STARTED).target(State.WAITING_VALIDATION)
				.event(Event.TransferAccepted)
				.action(stateMachineAction.transferAcceptedAction())
			.and().withExternal()
				.source(State.WAITING_VALIDATION).target(State.DOING_VALIDATION)
				.event(Event.ValidationStarted)
				.action(stateMachineAction.validationStartedAction())
			.and().withExternal()
				.source(State.DOING_VALIDATION).target(State.FINISHED_VALIDATION)
				.event(Event.ValidationSucceded)
				.action(stateMachineAction.validationSuccededAction())
			.and().withFork()
				.source(State.FINISHED_VALIDATION).target(State.PARALLEL_TASKS)
			.and().withExternal()
				.source(State.WAITING_SIGNALING).target(State.DOING_SIGNALING)
				.event(Event.SignalingStarted)
				.action(stateMachineAction.signalingStartedAction())
			.and().withExternal()
				.source(State.DOING_SIGNALING).target(State.FINISHED_SIGNALING)
				.event(Event.SignalingSucceded)
				.action(stateMachineAction.signalingSuccededAction())
			.and().withExternal()
				.source(State.WAITING_BALANCE).target(State.DOING_BALANCE)
				.event(Event.BalanceStarted)
				.action(stateMachineAction.balanceStartedAction())
			.and().withExternal()
				.source(State.DOING_BALANCE).target(State.FINISHED_BALANCE)
				.event(Event.BalanceSucceded)
				.action(stateMachineAction.balanceSuccededAction())
			.and().withJoin()
				.source(State.PARALLEL_TASKS).target(State.JOIN_PARALLEL_TASKS)
			.and().withExternal()
				.source(State.JOIN_PARALLEL_TASKS).target(State.WAITING_RECEIPT)
			.and().withExternal()
				.source(State.WAITING_RECEIPT).target(State.DOING_RECEIPT)
				.event(Event.ReceiptStarted)
				.action(stateMachineAction.receiptStartedAction())
			.and().withExternal()
				.source(State.DOING_RECEIPT).target(State.FINISHED_RECEIPT)
				.event(Event.ReceiptSucceded)
				.action(stateMachineAction.receiptSuccededAction())	
			// --------- Error Handling Validation  ---------
			.and().withExternal()
				.source(State.DOING_VALIDATION).target(State.ERROR_VALIDATION)
				.event(Event.ValidationError)
				.action(stateMachineAction.validationErrorAction())
			.and().withExternal()
				.source(State.ERROR_VALIDATION).target(State.WAITING_VALIDATION)
				.event(Event.AdhocValidationRequested)
				.action(stateMachineAction.adhocValidationRequestedAction())
			.and().withExternal()
				.source(State.DOING_VALIDATION).target(State.FAILED_VALIDATION)
				.event(Event.ValidationFailed)
				.action(stateMachineAction.validationFailedAction())
			// --------- Error Handling Signaling ---------
			.and().withExternal()
				.source(State.DOING_SIGNALING).target(State.ERROR_SIGNALING)
				.event(Event.SignalingError)
				.action(stateMachineAction.signalingErrorAction())
			.and().withExternal()
				.source(State.ERROR_SIGNALING).target(State.WAITING_SIGNALING)
				.event(Event.AdhocSignalingRequested)
				.action(stateMachineAction.adhocSignalingRequestedAction())
			// --------- Error Handling Balance ---------
			.and().withExternal()
				.source(State.DOING_BALANCE).target(State.ERROR_BALANCE)
				.event(Event.BalanceError)
				.action(stateMachineAction.balanceErrorAction())
			// --------- Error Handling Undo All ---------
			.and().withExternal()
				.source(State.ERROR_SIGNALING).target(State.UNDO_ALL)
				.event(Event.UndoAllRequested)
				.action(stateMachineAction.undoAllAction())
			.and().withExternal()
				.source(State.ERROR_BALANCE).target(State.UNDO_ALL)
				.event(Event.UndoAllRequested)
				.action(stateMachineAction.undoAllAction())
            .and().withExternal()
                .source(State.ERROR_RECEIPT).target(State.UNDO_ALL)
                .event(Event.UndoAllRequested)
                .action(stateMachineAction.undoAllAction())
			.and().withFork()
				.source(State.UNDO_ALL).target(State.PARALLEL_TASKS_UNDO)
            .and().withExternal()
                .source(State.WAITING_UNDOING_BALANCE).target(State.UNDOING_BALANCE)
                .event(Event.BalanceUndoStarted)
				.action(stateMachineAction.balanceUndoStartedAction())
			.and().withExternal()
				.source(State.UNDOING_BALANCE).target(State.FINISHED_UNDOING_BALANCE)
				.event(Event.BalanceUndoFinished)
				.action(stateMachineAction.balanceUndoFinishedAction())
            .and().withExternal()
                .source(State.WAITING_UNDOING_SIGNALING).target(State.UNDOING_SIGNALING)
                .event(Event.SignalingUndoStarted)
				.action(stateMachineAction.signalingUndoStartedAction())
            .and().withExternal()
                .source(State.UNDOING_SIGNALING).target(State.FINISHED_UNDOING_SIGNALING)
                .event(Event.SignalingUndoSucceded)
				.action(stateMachineAction.signalingUndoFinishedAction())
            .and().withJoin()
				.source(State.PARALLEL_TASKS_UNDO).target(State.JOIN_UNDO)
			.and().withExternal()
				.source(State.JOIN_UNDO).target(State.UNDO_ALL_FINISHED)
			.and().withInternal()
				.source(State.UNDO_ALL_FINISHED)
				.action(stateMachineAction.transferFailedAction())
			.and().withExternal()
                .source(State.UNDO_ALL_FINISHED).target(State.TRANSFER_FAILED)
				.event(Event.TransferFailed)
            // --------- Error Handling Receipt ---------
            .and().withExternal()
                .source(State.DOING_RECEIPT).target(State.ERROR_RECEIPT)
                .event(Event.ReceiptError)
                .action(stateMachineAction.receiptErrorAction())
            .and().withExternal()
                .source(State.ERROR_RECEIPT).target(State.WAITING_RECEIPT)
                .event(Event.AdhocReceiptRequested)
                .action(stateMachineAction.adhocReceiptRequestedAction());
    }

}
