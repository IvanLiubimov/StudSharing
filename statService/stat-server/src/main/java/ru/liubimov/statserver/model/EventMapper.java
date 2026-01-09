package ru.liubimov.statserver.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EventStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public EventStat toEntity(EventStatDto dto) {
        EventStat entity = new EventStat();
        entity.setEventType(dto.getEventType());
        entity.setDateTime(toInstant(dto.getDateTime()));
        entity.setPayloadJson(dto.getPayload());
        return entity;
    }

    public EventStatDto toDto(EventStat entity) {
        EventStatDto dto = new EventStatDto();
        dto.setEventType(entity.getEventType());
        dto.setDateTime(toLocalDateTime(entity.getDateTime()));
        dto.setPayload(entity.getPayloadJson());
        return dto;
    }

    private Instant toInstant(LocalDateTime dateTime) {
        if(dateTime != null){
            return dateTime.atZone(ZoneId.systemDefault()).toInstant();
        } else {
            return null;
        }
    }

    private LocalDateTime toLocalDateTime(Instant instant) {
        return instant != null
                ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                : null;
    }
}
