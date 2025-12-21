package ru.liubimov.statserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EventStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public EventStat toEntity(EventStatDto dto) {
        EventStat entity = new EventStat();
        entity.setEventType(dto.getType());
        entity.setDateTime(dto.getTimestamp());
        try {
            String json = objectMapper.writeValueAsString(dto.getPayload());
            entity.setPayloadJson(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload to JSON", e);
        }


        return entity;
    }

    public EventStatDto toDto(EventStat entity) {
        EventStatDto dto = new EventStatDto();
        dto.setType(entity.getEventType());
        dto.setTimestamp(entity.getDateTime());
        try {
            var payloadMap = objectMapper.readValue(
                    entity.getPayloadJson(),
                    Map.class
            );
            dto.setPayload(payloadMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize payload JSON", e);
        }

        return dto;
    }
}
