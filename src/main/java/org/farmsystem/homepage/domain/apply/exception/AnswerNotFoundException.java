package org.farmsystem.homepage.domain.apply.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;

public class AnswerNotFoundException extends EntityNotFoundException {

    public AnswerNotFoundException() {super(ErrorCode.ANSWER_NOT_FOUND);}
}
