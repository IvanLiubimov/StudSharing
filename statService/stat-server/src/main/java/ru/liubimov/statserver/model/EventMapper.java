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
        entity.setEventType(dto.getEventType());
        entity.setDateTime(dto.getDateTime());
        entity.setPayloadJson(dto.getPayload());
        return entity;
    }

    public EventStatDto toDto(EventStat entity) {
        EventStatDto dto = new EventStatDto();
        dto.setEventType(entity.getEventType());
        dto.setDateTime(entity.getDateTime());
        dto.setPayload(entity.getPayloadJson());
        return dto;
    }
}
