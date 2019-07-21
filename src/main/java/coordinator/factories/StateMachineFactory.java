package coordinator.factories;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import coordinator.interfaces.StateMachineActions;
import coordinator.models.Events;
import coordinator.models.States;

public class StateMachineFactory {

    private StateMachineActions stateMachineActions;

    public StateMachineFactory(StateMachineActions stateMachineActions) {
        this.stateMachineActions = stateMachineActions;
    }

    public StateMachine<States, Events> buildStateMachine() throws Exception {
        Builder<States, Events> builder = StateMachineBuilder.builder();

        configure(builder.configureStates());
        configure(builder.configureTransitions());

        return builder.build();
    }

    private void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
            .withStates()
                .initial(States.MONITORING_NOT_STARTED)
                .state(States.WAITING_VALIDATION)
                .state(States.DOING_VALIDATION)
                .state(States.ERROR_VALIDATION)
                .state(States.PARALLEL_TASKS)
                .state(States.WAITING_RECEIPT)
                .state(States.DOING_RECEIPT)
                .state(States.ERROR_RECEIPT)
                .state(States.PARALLEL_TASKS_UNDO)
                .state(States.UNDO_ALL_FINISHED)
                .fork(States.FINISHED_VALIDATION)
                .join(States.JOIN_PARALLEL_TASKS)
                .fork(States.UNDO_ALL)
                .join(States.JOIN_UNDO)
                .end(States.FINISHED_RECEIPT)
                .end(States.FAILED_VALIDATION)
                .end(States.TRANSFER_FAILED)
                .and().withStates()
                    .parent(States.PARALLEL_TASKS)
                    .initial(States.WAITING_SIGNALING)
                    .state(States.DOING_SIGNALING)
                    .state(States.ERROR_SIGNALING)
                    .end(States.FINISHED_SIGNALING)
                .and().withStates()
                    .parent(States.PARALLEL_TASKS)
                    .initial(States.WAITING_BALANCE)
                    .state(States.DOING_BALANCE)
                    .state(States.ERROR_BALANCE)
                    .end(States.FINISHED_BALANCE)
                .and().withStates()
                    .parent(States.PARALLEL_TASKS_UNDO)
                    .initial(States.WAITING_UNDOING_BALANCE)
                    .state(States.UNDOING_BALANCE)
                    .end(States.FINISHED_UNDOING_BALANCE)
                .and().withStates()
                    .parent(States.PARALLEL_TASKS_UNDO)
                    .initial(States.WAITING_UNDOING_SIGNALING)
                    .state(States.UNDOING_SIGNALING)
                    .end(States.FINISHED_UNDOING_SIGNALING);
    }

    private void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal()
				.source(States.MONITORING_NOT_STARTED).target(States.WAITING_VALIDATION)
				.event(Events.TransferAccepted)
				.action(stateMachineActions.transferAcceptedAction())
			.and().withExternal()
				.source(States.WAITING_VALIDATION).target(States.DOING_VALIDATION)
				.event(Events.ValidationStarted)
				.action(stateMachineActions.validationStartedAction())
			.and().withExternal()
				.source(States.DOING_VALIDATION).target(States.FINISHED_VALIDATION)
				.event(Events.ValidationSucceded)
				.action(stateMachineActions.validationSuccededAction())
			.and().withFork()
				.source(States.FINISHED_VALIDATION).target(States.PARALLEL_TASKS)
			.and().withExternal()
				.source(States.WAITING_SIGNALING).target(States.DOING_SIGNALING)
				.event(Events.SignalingStarted)
				.action(stateMachineActions.signalingStartedAction())
			.and().withExternal()
				.source(States.DOING_SIGNALING).target(States.FINISHED_SIGNALING)
				.event(Events.SignalingSucceded)
				.action(stateMachineActions.signalingSuccededAction())
			.and().withExternal()
				.source(States.WAITING_BALANCE).target(States.DOING_BALANCE)
				.event(Events.BalanceStarted)
				.action(stateMachineActions.balanceStartedAction())
			.and().withExternal()
				.source(States.DOING_BALANCE).target(States.FINISHED_BALANCE)
				.event(Events.BalanceSucceded)
				.action(stateMachineActions.balanceSuccededAction())
			.and().withJoin()
				.source(States.PARALLEL_TASKS).target(States.JOIN_PARALLEL_TASKS)
			.and().withExternal()
				.source(States.JOIN_PARALLEL_TASKS).target(States.WAITING_RECEIPT)
			.and().withExternal()
				.source(States.WAITING_RECEIPT).target(States.DOING_RECEIPT)
				.event(Events.ReceiptStarted)
				.action(stateMachineActions.receiptStartedAction())
			.and().withExternal()
				.source(States.DOING_RECEIPT).target(States.FINISHED_RECEIPT)
				.event(Events.ReceiptSucceded)
				.action(stateMachineActions.receiptSuccededAction())	
			// --------- Error Handling Validation  ---------
			.and().withExternal()
				.source(States.DOING_VALIDATION).target(States.ERROR_VALIDATION)
				.event(Events.ValidationError)
				.action(stateMachineActions.validationErrorAction())
			.and().withExternal()
				.source(States.ERROR_VALIDATION).target(States.WAITING_VALIDATION)
				.event(Events.AdhocValidationRequested)
				.action(stateMachineActions.adhocValidationRequestedAction())
			.and().withExternal()
				.source(States.DOING_VALIDATION).target(States.FAILED_VALIDATION)
				.event(Events.ValidationFailed)
				.action(stateMachineActions.validationFailedAction())
			// --------- Error Handling Signaling ---------
			.and().withExternal()
				.source(States.DOING_SIGNALING).target(States.ERROR_SIGNALING)
				.event(Events.SignalingError)
				.action(stateMachineActions.signalingErrorAction())
			.and().withExternal()
				.source(States.ERROR_SIGNALING).target(States.WAITING_SIGNALING)
				.event(Events.AdhocSignalingRequested)
				.action(stateMachineActions.adhocSignalingRequestedAction())
			// --------- Error Handling Balance ---------
			.and().withExternal()
				.source(States.DOING_BALANCE).target(States.ERROR_BALANCE)
				.event(Events.BalanceError)
				.action(stateMachineActions.balanceErrorAction())
			// --------- Error Handling Undo All ---------
			.and().withExternal()
				.source(States.ERROR_SIGNALING).target(States.UNDO_ALL)
				.event(Events.UndoAllRequested)
				.action(stateMachineActions.undoAllAction())
			.and().withExternal()
				.source(States.ERROR_BALANCE).target(States.UNDO_ALL)
				.event(Events.UndoAllRequested)
				.action(stateMachineActions.undoAllAction())
            .and().withExternal()
                .source(States.ERROR_RECEIPT).target(States.UNDO_ALL)
                .event(Events.UndoAllRequested)
                .action(stateMachineActions.undoAllAction())
			.and().withFork()
				.source(States.UNDO_ALL).target(States.PARALLEL_TASKS_UNDO)
            .and().withExternal()
                .source(States.WAITING_UNDOING_BALANCE).target(States.UNDOING_BALANCE)
                .event(Events.BalanceUndoStarted)
				.action(stateMachineActions.balanceUndoStartedAction())
			.and().withExternal()
				.source(States.UNDOING_BALANCE).target(States.FINISHED_UNDOING_BALANCE)
				.event(Events.BalanceUndoFinished)
				.action(stateMachineActions.balanceUndoFinishedAction())
            .and().withExternal()
                .source(States.WAITING_UNDOING_SIGNALING).target(States.UNDOING_SIGNALING)
                .event(Events.SignalingUndoStarted)
				.action(stateMachineActions.signalingUndoStartedAction())
            .and().withExternal()
                .source(States.UNDOING_SIGNALING).target(States.FINISHED_UNDOING_SIGNALING)
                .event(Events.SignalingUndoSucceded)
				.action(stateMachineActions.signalingUndoFinishedAction())
            .and().withJoin()
				.source(States.PARALLEL_TASKS_UNDO).target(States.JOIN_UNDO)
			.and().withExternal()
				.source(States.JOIN_UNDO).target(States.UNDO_ALL_FINISHED)
			.and().withInternal()
				.source(States.UNDO_ALL_FINISHED)
				.action(stateMachineActions.transferFailedAction())
			.and().withExternal()
                .source(States.UNDO_ALL_FINISHED).target(States.TRANSFER_FAILED)
				.event(Events.TransferFailed)
            // --------- Error Handling Receipt ---------
            .and().withExternal()
                .source(States.DOING_RECEIPT).target(States.ERROR_RECEIPT)
                .event(Events.ReceiptError)
                .action(stateMachineActions.receiptErrorAction())
            .and().withExternal()
                .source(States.ERROR_RECEIPT).target(States.WAITING_RECEIPT)
                .event(Events.AdhocReceiptRequested)
                .action(stateMachineActions.adhocReceiptRequestedAction())
				;
    }

}
