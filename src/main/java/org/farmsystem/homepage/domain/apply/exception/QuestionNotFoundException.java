package org.farmsystem.homepage.domain.apply.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;

public class QuestionNotFoundException extends EntityNotFoundException {

    public QuestionNotFoundException() {super(ErrorCode.QUESTION_NOT_FOUND);}
}
