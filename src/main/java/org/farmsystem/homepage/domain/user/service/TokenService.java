package org.farmsystem.homepage.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserTokenRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.global.config.auth.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    public String issueNewAccessToken(Long userId) {
        return jwtProvider.getIssueToken(userId, true);
    }

    public String issueNewRefreshToken(Long userId) {
        return jwtProvider.getIssueToken(userId, false);
    }

    public UserTokenResponseDTO issueTempToken(Long userId) {
        String accessToken = issueNewAccessToken(userId);
        String refreshToken = issueNewRefreshToken(userId);
        return UserTokenResponseDTO.of(accessToken, refreshToken);
    }

    public UserTokenResponseDTO issueToken(Long userId) {

        String accessToken = issueNewAccessToken(userId);

        String redisKey = "RT:" + userId;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);

        if (storedRefreshToken == null) {
            storedRefreshToken = issueNewRefreshToken(userId);
            redisTemplate.opsForValue().set(redisKey, storedRefreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        return UserTokenResponseDTO.of(accessToken, storedRefreshToken);
    }

    public UserTokenResponseDTO reissue(UserTokenRequestDTO uerTokenRequest) throws JsonProcessingException {
        Long userId = Long.valueOf(jwtProvider.decodeJwtPayloadSubject(uerTokenRequest.accessToken()));

        String refreshToken = uerTokenRequest.refreshToken();
        String redisKey = "RT:" + userId;

        jwtProvider.validateRefreshToken(refreshToken);

        String newAccessToken = issueNewAccessToken(userId);
        String newRefreshToken = issueNewRefreshToken(userId);
        redisTemplate.opsForValue().set(redisKey, newRefreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);
        jwtProvider.equalsRefreshToken(newRefreshToken, storedRefreshToken);

        return UserTokenResponseDTO.of(newAccessToken, newRefreshToken);
    }

    public void logout(Long userId) {
        String redisKey = "RT:" + userId;
        redisTemplate.delete(redisKey);
    }
}
