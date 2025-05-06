package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.entity.SeedEventType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeedEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // seed-event 메시지를 Kafka에 전송
    public void sendSeedEvent(Long userId, SeedEventType type) {
        String message = userId + ":" + type.name();
        kafkaTemplate.send("seed-event", message);
    }
}

