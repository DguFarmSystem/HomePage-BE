package org.farmsystem.homepage.global.error.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
public class IllegalArgumentException extends BusinessException {
    public IllegalArgumentException() {
        super(ErrorCode.BAD_REQUEST);
    }
    public IllegalArgumentException(ErrorCode errorCode) {super(errorCode);}
}
