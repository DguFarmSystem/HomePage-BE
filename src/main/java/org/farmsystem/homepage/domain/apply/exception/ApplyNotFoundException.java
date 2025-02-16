package org.farmsystem.homepage.domain.apply.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;

public class ApplyNotFoundException extends EntityNotFoundException {

    public ApplyNotFoundException() {super(ErrorCode.APPLY_NOT_FOUND);}
}
