package org.farmsystem.homepage.domain.community.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("팜 회원"),
    ADMIN("관리자");

    private final String title;

    @Override
    public String getAuthority() {
        return name();
    }
}
