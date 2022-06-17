package com.homebrew.metadata.exception.handler;

import com.homebrew.metadata.model.exception.ExceptionDetails;
import com.homebrew.metadata.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    private ExceptionDetails exceptionDetails;

    @Autowired
    public DefaultExceptionHandler(ExceptionDetails exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    @ExceptionHandler(value = HttpStatusCodeException.class)
    public ResponseEntity<ExceptionDetails> handleException(HttpStatusCodeException ex) {
        //Handling 4XX and 5XX series errors
        exceptionDetails.setCode(String.valueOf(ex.getStatusCode().value()));
        exceptionDetails.setCause(ex.getMessage());
        if (ex instanceof HttpClientErrorException) {
            log.error("Client error occurred - Homebrew API", ex);
            exceptionDetails.setSource(Constants.CLINET_ERROR);
        } else if (ex instanceof HttpServerErrorException) {
            log.error("Server error occurred - Homebrew API", ex);
            exceptionDetails.setSource(Constants.SERVER_ERROR);
        }

        log.info("Returning error response");
        return new ResponseEntity<ExceptionDetails>(exceptionDetails, ex.getStatusCode());

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDetails> handleException(Exception ex) {
        //Generic exception handling
        log.error("Error occurred - Metadata API", ex);
        exceptionDetails.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        exceptionDetails.setCause(HttpStatus.INTERNAL_SERVER_ERROR.name());
        exceptionDetails.setSource(Constants.SERVER_ERROR);

        log.info("Returning error response");
        return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
