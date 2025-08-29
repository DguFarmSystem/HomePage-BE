package org.farmsystem.homepage.domain.community.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.community.user.entity.SeedEventType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeedEventConsumer {

    private final StringRedisTemplate redisTemplate;

    // Kafka에서 받은 메세지 처리
    @KafkaListener(topics = "seed-event", groupId = "seed-event-group")
    public void processSeedEvent(String message) {
        try {
            String[] parts = message.split(":");
            Long userId = Long.parseLong(parts[0]);
            SeedEventType type = SeedEventType.valueOf(parts[1]);

            // 해당 유저에서 발생한 씨앗 이벤트의 개수만큼 점수 증가
            redisTemplate.opsForZSet().incrementScore("user-ranking", userId.toString(), type.getSeedAmount());
        } catch (Exception e) {
            log.error("Kafka 메시지 에러: {}", message, e);
        }
    }
}

