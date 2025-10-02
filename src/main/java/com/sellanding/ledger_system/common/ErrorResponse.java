package com.sellanding.ledger_system.common;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorResponse {
    
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
    private final List<ValidationError> validationErrors;

    private ErrorResponse(int status, String error, String Message, List<ValidationError> validationErrors) {
        this.status = status;
        this.error = error;
        this.message = Message;
        this.validationErrors = validationErrors;
    }

    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message, null);
    }

    public static ErrorResponse of(HttpStatus httpStatus, String message, List<FieldError> fieldErrors) {
        List<ValidationError> validationErrorList = (fieldErrors != null && !fieldErrors.isEmpty())
            ? fieldErrors.stream().map(ValidationError::of).toList()
            : null;
        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message, validationErrorList);
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public List<ValidationError> getValidationErrors() { return validationErrors; }

    public static final class ValidationError {
        private final String field;
        private final String message;

        private ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public static ValidationError of(final FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        public String getField() { return field; }
        public String getMessage() { return message; }
    }
}
