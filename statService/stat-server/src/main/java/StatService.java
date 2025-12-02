import model.EventStat;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatService {
    BookingDtoStat getBookingsStat(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> types);

    RoomStatisticDto getRoomStatisticById(Long roomId);

    EngineerStatisticDto getEngineerStatisticById(Long engineerId);

    PeakHoursDto getMostPopularHours();

    StatSummaryDto getStatSummary();

    EventStat saveEventStat(EventStatDto eventStatDto);
}
