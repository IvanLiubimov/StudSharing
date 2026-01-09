package dto.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingStat {
    private Long bookingId;          // id брони
    private Long userId;             // id пользователя
    private Long roomId;             // id комнаты
    private Long engineerId;         // id звукорежиссера (null, если без)
    private LocalDateTime startTime; // начало брони
    private LocalDateTime endTime;   // конец брони
}

