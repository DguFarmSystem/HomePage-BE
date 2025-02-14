package org.farmsystem.homepage.domain.apply.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;

public class ApplyAlreadySubmittedException extends BusinessException {

    public ApplyAlreadySubmittedException() {super(ErrorCode.APPLY_ALREADY_SUBMITTED);}
}
