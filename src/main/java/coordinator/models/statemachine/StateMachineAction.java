package coordinator.models.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import coordinator.models.messaging.MessageHeader;
import coordinator.models.messaging.ProducerResult;
import coordinator.models.messaging.ProducerService;
import coordinator.repositories.PayloadRepository;
import coordinator.schemas.transfer.TransferAccepted;
import coordinator.schemas.transfer.TransferError;
import coordinator.schemas.undo.UndoAllRequested;
import coordinator.utils.SpringMessageTools;
import coordinator.models.transitions.Event;
import coordinator.models.transitions.State;

@Configuration
public class StateMachineAction implements StateAction {
	
	private ProducerService producer;

	@Autowired
	private PayloadRepository payloadsRepository;

	public Action<State, Event> transferAcceptedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> validationStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> validationSuccededAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> balanceStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> balanceSuccededAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> signalingStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> signalingSuccededAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> receiptStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> joiningAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> receiptSuccededAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> transferSuccededAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> transferFailedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {
				String message = "Transfer has failed! Sorry for that!";
				ProducerResult result = sendTransferFailedMessage(context, message);

				if (result.isValid()) {
					System.out.println("transferFailedAction sent message successfully");
				} else {
					System.out.println(result.getErrorMessage());
					System.out.println(result.getDetailedErrorMessage());
				}
			}
		};
	}

	public Action<State, Event> validationErrorAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> signalingErrorAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {
				ProducerResult result = sendUndoAllRequestMessage(context);

				if (result.isValid()) {
					System.out.println("signalingErrorAction sent message successfully");
				} else {
					System.out.println(result.getErrorMessage());
					System.out.println(result.getDetailedErrorMessage());
				}
			}
		};
	}

	@Override
	public Action<State, Event> balanceErrorAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {
				ProducerResult result = sendUndoAllRequestMessage(context);

				if (result.isValid()) {
					System.out.println("balanceErrorAction sent message successfully");
				} else {
					System.out.println(result.getErrorMessage());
					System.out.println(result.getDetailedErrorMessage());
				}
			}
		};
	}

	public Action<State, Event> receiptErrorAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {
				ProducerResult result = sendUndoAllRequestMessage(context);

				if (result.isValid()) {
					System.out.println("receiptErrorAction sent message successfully");
				} else {
					System.out.println(result.getErrorMessage());
					System.out.println(result.getDetailedErrorMessage());
				}
			}
		};
	}

	public Action<State, Event> adhocValidationRequestedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};

	}

	public Action<State, Event> adhocSignalingRequestedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> adhocReceiptRequestedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> undoAllAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {
			}
		};
	}

	public Action<State, Event> balanceUndoStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> balanceUndoFinishedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> signalingUndoStartedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> signalingUndoFinishedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	public Action<State, Event> validationFailedAction() {
		return new Action<State, Event>() {
			@Override
			public void execute(StateContext<State, Event> context) {

			}
		};
	}

	private ProducerResult sendTransferFailedMessage(StateContext<State, Event> context, String textMessage) {
		String transactionId = (String) context.getExtendedState().getVariables().get(SpringMessageTools.transactionId);

		Message<?> transferAcceptedMessage = payloadsRepository
			.FindOneByFilter("eventType", TransferAccepted.class.getSimpleName(), "transactionId", transactionId);
				
		TransferAccepted transferAccepted = (TransferAccepted) transferAcceptedMessage.getPayload();
		TransferError transferError = createTransferErrorFromTransfer(transferAccepted, textMessage);
		MessageHeader header = createHeaderFromTransfer(transferAccepted, TransferError.class.getSimpleName());

		Message<TransferError> message = MessageBuilder
                .withPayload(transferError)
                .setHeader(KafkaHeaders.MESSAGE_KEY, header)
				.build();
				
		return producer.send(message);
	}

	private ProducerResult sendUndoAllRequestMessage(StateContext<State, Event> context) {
		String transactionId = SpringMessageTools.extractTransactionId(context);

		Message<?> transferAcceptedMessage = payloadsRepository
			.FindOneByFilter("eventType", TransferAccepted.class.getSimpleName(), "transactionId", transactionId);

		TransferAccepted transferAccepted = (TransferAccepted) transferAcceptedMessage.getPayload();
		UndoAllRequested undoAllRequested = createUndoAllRequestFromTransfer(transferAccepted);
		MessageHeader header = createHeaderFromTransfer(transferAccepted, UndoAllRequested.class.getSimpleName());

		Message<UndoAllRequested> message = MessageBuilder
                .withPayload(undoAllRequested)
                .setHeader(KafkaHeaders.MESSAGE_KEY, header)
				.build();

		return producer.send(message);
	}

	private MessageHeader createHeaderFromTransfer(TransferAccepted transferAccepted, String eventType) {
		MessageHeader header = new MessageHeader();

		header.setTransactionId(transferAccepted.get("transactionId").toString());
		header.setCorrelationId(transferAccepted.get("correlationId").toString());
		header.setEventType(eventType);
		header.setSource("Monitor");

		return header;
	}

	private UndoAllRequested createUndoAllRequestFromTransfer(TransferAccepted transferAccepted) {
		UndoAllRequested undoAllRequested = new UndoAllRequested();

		undoAllRequested.setTransactionId(transferAccepted.get("transactionId").toString());
		undoAllRequested.setCorrelationId(transferAccepted.get("correlationId").toString());
		undoAllRequested.setAccountFromId(transferAccepted.get("accountFromId").toString());
		undoAllRequested.setAccountToId(transferAccepted.get("accountToId").toString());
		undoAllRequested.setAmount(transferAccepted.get("amount").toString());

		return undoAllRequested;
	}

	private TransferError createTransferErrorFromTransfer(TransferAccepted transferAccepted, String message) {
		TransferError transferError = new TransferError();

		transferError.setTransactionId(transferAccepted.getTransactionId());
		transferError.setCorrelationId(transferAccepted.getCorrelationId());
		transferError.setMessage(message);
		
		return transferError;
	}
}
