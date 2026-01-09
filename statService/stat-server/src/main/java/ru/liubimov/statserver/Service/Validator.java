package ru.liubimov.statserver.Service;

import dto.EventStatDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Validator {

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1999, 1, 1, 0, 0);

    public void dtoValidate(EventStatDto eventStatDto) {
        if (eventStatDto.getEventType() == null || eventStatDto.getEventType().isBlank()) {
            throw new IllegalArgumentException("Type не может быть null или пустым");
        }
        if (eventStatDto.getDateTime() == null || eventStatDto.getDateTime().isBefore(MIN_DATE)) {
            throw new IllegalArgumentException("Некорректно введены дата и время");
        }
        if (eventStatDto.getPayload() == null || eventStatDto.getPayload().isEmpty()) {
            throw new IllegalArgumentException("Payload не может быть null или пустым");
        }
    }

    public void requestValidator(LocalDateTime startDateTime,
                                 LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            throw new IllegalArgumentException("Начало и конец периода не могут быть null");
        }
        if (startDateTime.isBefore(MIN_DATE) ) {
            throw new IllegalArgumentException("Дата начала не может быть раньше \" + MIN_DATE");
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Дата начала должна быть раньше даты окончания");
        }
    }

}
