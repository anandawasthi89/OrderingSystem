package com.myecom.oms.commons;


public class NoPendingJobsException extends RuntimeException {
    public NoPendingJobsException(String message) {
        super(message);
    }
}
