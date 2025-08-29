package org.farmsystem.homepage.domain.community.user.dto.response;

import org.farmsystem.homepage.domain.common.dto.response.PageResponseDTO;

import java.util.List;

public record PagingUserListResponseDTO(
        PageResponseDTO page,
        List<UserInfoResponseDTO> userList
) {
    public static PagingUserListResponseDTO of(PageResponseDTO page, List<UserInfoResponseDTO> userList) {
        return new PagingUserListResponseDTO(page, userList);
    }
}
