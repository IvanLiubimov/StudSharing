package dto;

import dto.payload.BookingStat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EventStatDto {
    private LocalDateTime dateTime;
    private String EventType;
    private BookingStat payload;
}
