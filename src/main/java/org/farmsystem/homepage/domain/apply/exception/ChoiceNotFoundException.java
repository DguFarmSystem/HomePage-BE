package org.farmsystem.homepage.domain.apply.exception;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;

public class ChoiceNotFoundException extends EntityNotFoundException {

    public ChoiceNotFoundException() {super(ErrorCode.CHOICE_NOT_FOUND);}
}
