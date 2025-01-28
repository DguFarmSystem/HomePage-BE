package org.farmsystem.homepage.global.error.exception;


import org.farmsystem.homepage.global.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}