package coordinator.models.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import coordinator.models.TransferPayload;
import coordinator.models.messaging.ProducerResult;
import coordinator.models.messaging.ProducerService;
import coordinator.models.payloads.transfer.TransferAccepted;
import coordinator.repositories.PayloadRepository;
import coordinator.shared.avro.messages.Message;
import coordinator.shared.avro.messages.headers.MessageHeader;
import coordinator.shared.avro.serializers.AvroSerializer;
import coordinator.utils.SpringMessageTools;
import coordinator.models.payloads.transfer.TransferError;
import coordinator.models.payloads.undo.UndoAllRequested;
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

		TransferPayload transferAcceptedRecord = payloadsRepository
			.FindOneByFilter("eventType", TransferAccepted.class.getSimpleName(), "transactionId", transactionId);
				
		TransferAccepted transferAccepted = deserializeTransferPayload(transferAcceptedRecord);

		TransferError tranferError = createTransferErrorFromTransfer(transferAccepted, textMessage);

		MessageHeader header = createHeaderFromTransfer(transferAccepted, TransferError.class.getSimpleName());

		Message<TransferError> message = new Message<>(header, tranferError);
		byte[] msgBytes = AvroSerializer.serialize(message);

		// TODO: Use dependency injection to autowire producerService
		// ProducerService<String, byte[]> producerService = new ProducerService<>(MessagingConfiguration.TOPIC);
		// producerService.send(tranferError.getCorrelationId(), msgBytes);

		return producer.send(msgBytes);
	}

	private ProducerResult sendUndoAllRequestMessage(StateContext<State, Event> context) {
		TransferPayload transferAcceptedRecord = findTransferAcceptedRecord(context);

		TransferAccepted transferAccepted = deserializeTransferPayload(transferAcceptedRecord);

		UndoAllRequested undoAllRequested = createUndoAllRequestFromTransfer(transferAccepted);

		MessageHeader header = createHeaderFromTransfer(transferAccepted, UndoAllRequested.class.getSimpleName());

		Message<UndoAllRequested> message = new Message<>(header, undoAllRequested);
		byte[] msgBytes = AvroSerializer.serialize(message);

		// TODO: Use dependency injection to autowire producerService
		// ProducerService<String, byte[]> producerService = new ProducerService<>(MessagingConfiguration.TOPIC);
		// producerService.send(undoAllRequested.getCorrelationId(), msgBytes);

		return producer.send(msgBytes);
	}

	private TransferAccepted deserializeTransferPayload(TransferPayload transferAcceptedRecord) {
		byte[] messageBytes = transferAcceptedRecord.getMessageBytes();
		TransferAccepted transferAccepted = AvroSerializer.deserializePayload(messageBytes);
		return transferAccepted;
	}

	private TransferPayload findTransferAcceptedRecord(StateContext<State, Event> context) {
		String transactionId = SpringMessageTools.extractTransactionId(context);

		TransferPayload transferAcceptedRecord = payloadsRepository.FindOneByFilter("eventType",
				TransferAccepted.class.getSimpleName(), "transactionId", transactionId);
		return transferAcceptedRecord;
	}

	private MessageHeader createHeaderFromTransfer(TransferAccepted transferAccepted, String eventType) {
		MessageHeader header = new MessageHeader();
		header.setTransactionId(transferAccepted.getTransactionId());
		header.setCorrelationId(transferAccepted.getCorrelationId());
		header.setEventType(eventType);
		header.setSource("Monitor");
		return header;
	}

	private UndoAllRequested createUndoAllRequestFromTransfer(TransferAccepted transferAccepted) {
		UndoAllRequested undoAllRequested = new UndoAllRequested();
		undoAllRequested.setTransactionId(transferAccepted.getTransactionId());
		undoAllRequested.setCorrelationId(transferAccepted.getCorrelationId());
		undoAllRequested.setAccountFromId(transferAccepted.getAccountFromId());
		undoAllRequested.setAccountToId(transferAccepted.getAccountToId());
		undoAllRequested.setAmount(transferAccepted.getAmount());
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
