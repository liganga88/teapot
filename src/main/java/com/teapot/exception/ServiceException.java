package com.teapot.exception;

/**
 * Created by Administrator on 2017-12-11.
 */
public class ServiceException extends RuntimeException {

    private int errorCode;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        this.errorCode = 1000;
    }

    public ServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
