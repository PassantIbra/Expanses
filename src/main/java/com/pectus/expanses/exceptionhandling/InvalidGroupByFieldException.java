package com.pectus.expanses.exceptionhandling;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InExistentTransactionReceiverException class to handle exception caused by
 * user trying to make transaction to inexistent user .
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidGroupByFieldException extends InvalidDataAccessApiUsageException {
    public InvalidGroupByFieldException(String msg) {
        super(msg);
    }
}
