package org.rishbootdev.healthsphere.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildErrorResponse(String userMessage, Exception ex, WebRequest request,
                                                             HttpStatus status, String errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                status.value(),
                errorCode,
                userMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        logger.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "The requested resource or identity could not be found. Please verify the details and try again.",
                ex, request, HttpStatus.NOT_FOUND, ex.getErrorCode());
    }

    @ExceptionHandler(ChainCodeException.class)
    public ResponseEntity<ErrorResponse> handleChainCodeException(ChainCodeException ex, WebRequest request) {
        logger.error("ChainCodeException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "There was a problem executing the blockchain transaction. Please try again later.",
                ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getErrorCode());
    }

    @ExceptionHandler(LedgerAccessException.class)
    public ResponseEntity<ErrorResponse> handleLedgerAccessException(LedgerAccessException ex, WebRequest request) {
        logger.error("LedgerAccessException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Unable to access the blockchain ledger right now. Please wait a moment and retry.",
                ex, request, HttpStatus.BAD_GATEWAY, ex.getErrorCode());
    }

    @ExceptionHandler(NetworkConnectionException.class)
    public ResponseEntity<ErrorResponse> handleNetworkConnectionException(NetworkConnectionException ex, WebRequest request) {
        logger.error("NetworkConnectionException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "A network issue occurred while connecting to the blockchain. Please check your connection and try again.",
                ex, request, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, ex.getErrorCode());
    }

    @ExceptionHandler(TransactionSubmissionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionSubmissionException(TransactionSubmissionException ex, WebRequest request) {
        logger.error("TransactionSubmissionException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Your transaction could not be processed at this time. Please try again shortly.",
                ex, request, HttpStatus.SERVICE_UNAVAILABLE, ex.getErrorCode());
    }

    @ExceptionHandler(BlockchainException.class)
    public ResponseEntity<ErrorResponse> handleBlockchainException(BlockchainException ex, WebRequest request) {
        logger.error("BlockchainException: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "An unexpected blockchain error occurred. Please review your request or contact support if it continues.",
                ex, request, HttpStatus.BAD_REQUEST, ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled Exception: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Something went wrong on our side. Please try again later.",
                ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    }
}
