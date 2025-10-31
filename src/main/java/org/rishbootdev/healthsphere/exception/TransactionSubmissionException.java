package org.rishbootdev.healthsphere.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class TransactionSubmissionException extends BlockchainException {
    public TransactionSubmissionException(String message) {
        super(message, "TRANSACTION_SUBMISSION_ERROR");
    }
}

