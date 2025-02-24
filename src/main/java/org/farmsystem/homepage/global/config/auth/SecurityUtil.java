package org.farmsystem.homepage.global.config.auth;

import org.farmsystem.homepage.global.error.ErrorCode;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        return Long.parseLong(authentication.getPrincipal().toString());
    }
}
