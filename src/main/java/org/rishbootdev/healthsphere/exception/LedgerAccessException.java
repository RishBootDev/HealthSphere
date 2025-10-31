package org.rishbootdev.healthsphere.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class LedgerAccessException extends BlockchainException {
    public LedgerAccessException(String message) {
        super(message, "LEDGER_ACCESS_ERROR");
    }
}

