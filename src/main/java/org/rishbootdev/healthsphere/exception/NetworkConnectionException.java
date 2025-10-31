package org.rishbootdev.healthsphere.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class NetworkConnectionException extends BlockchainException {
    public NetworkConnectionException(String message) {
        super(message, "NETWORK_CONNECTION_ERROR");
    }
}

