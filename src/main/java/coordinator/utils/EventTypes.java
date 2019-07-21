package coordinator.utils;

public class EventTypes {
    
    // Transfer Service
    public static final String TransferSucceded = "TransferSucceded";
    public static final String TransferError = "TransferError";
    public static final String TransferAccepted = "TransferAccepted";

    // Validation Service
    public static final String AdhocValidationRequested = "AdhocValidationRequested";
    public static final String ValidationStarted = "ValidationStarted";
    public static final String AccountChecked = "AccountChecked";
    public static final String BalanceChecked = "BalanceChecked";
    public static final String ValidationSucceded = "ValidationSucceded";
    public static final String ValidationFailed = "ValidationFailed";
    public static final String ValidationError = "ValidationError";

    // Balance Service
    public static final String ValidationCompleted = "ValidationCompleted";
    public static final String BalanceUndo = "BalanceUndo";
    public static final String AdhocBalanceRequested = "AdhocBalanceRequested";
    public static final String BalanceStarted = "BalanceStarted";
    public static final String BalancePartialOperation = "BalancePartialOperation";
    public static final String BalanceError = "BalanceError";
    public static final String BalanceSucceded = "BalanceSucceded";

    // Signaling Service
    public static final String AdhocSignalingRequested = "AdhocSignalingRequested";
    public static final String SignalingStarted = "SignalingStarted";
    public static final String SignalingError = "SignalingError";
    public static final String SignalingSucceded = "SignalingSucceded";

    // Receipt Service
    public static final String AdhocReceiptRequested = "AdhocReceiptRequested";
    public static final String ReceiptStarted = "ReceiptStarted";
    public static final String ReceiptError = "ReceiptError";
    public static final String ReceiptSucceded = "ReceiptSucceded";

    // Account Validation (deprecated)
    public static final String ValidAccount = "ValidAccountEvent";
    public static final String InvalidAccount = "InvalidAccountEvent";

    // Undo
    public static final String UndoAllRequested = "UndoAllRequested";
}
