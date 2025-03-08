package org.farmsystem.homepage.domain.user.dto.response;

import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PagingUserListResponseDTO(
        PageResponseDTO page,
        List<UserInfoResponseDTO> userList
) {
    public static PagingUserListResponseDTO of(Page<User> userPage, Pageable pageable, List<UserInfoResponseDTO> filteredUser) {
        PageResponseDTO page = new PageResponseDTO(
                filteredUser.size(),
                pageable.getPageSize(),
                userPage.getTotalPages(),
                pageable.getPageNumber() + 1,
                pageable.getSort().toString()
        );
        return new PagingUserListResponseDTO(page, filteredUser);
    }
}
