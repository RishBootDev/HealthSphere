package org.rishbootdev.healthsphere.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChainCodeException extends BlockchainException {

    public ChainCodeException(String message) {
        super(message, "CHAINCODE_EXECUTION_ERROR");
    }
}
