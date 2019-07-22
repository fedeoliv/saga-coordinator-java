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
import coordinator.models.transitions.Events;
import coordinator.models.transitions.States;

@Configuration
public class StateMachineAction implements StateAction {
	
	private ProducerService producer;

	@Autowired
	private PayloadRepository payloadsRepository;

	public Action<States, Events> transferAcceptedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> validationStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> validationSuccededAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> balanceStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> balanceSuccededAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> signalingStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> signalingSuccededAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> receiptStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> joiningAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> receiptSuccededAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> transferSuccededAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> transferFailedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
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

	public Action<States, Events> validationErrorAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> signalingErrorAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
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
	public Action<States, Events> balanceErrorAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
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

	public Action<States, Events> receiptErrorAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
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

	public Action<States, Events> adhocValidationRequestedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};

	}

	public Action<States, Events> adhocSignalingRequestedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> adhocReceiptRequestedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> undoAllAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
			}
		};
	}

	public Action<States, Events> balanceUndoStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> balanceUndoFinishedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> signalingUndoStartedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> signalingUndoFinishedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	public Action<States, Events> validationFailedAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {

			}
		};
	}

	private ProducerResult sendTransferFailedMessage(StateContext<States, Events> context, String textMessage) {
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

	private ProducerResult sendUndoAllRequestMessage(StateContext<States, Events> context) {
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

	private TransferPayload findTransferAcceptedRecord(StateContext<States, Events> context) {
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
