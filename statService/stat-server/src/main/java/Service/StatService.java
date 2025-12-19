package Service;

import model.EventStat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatService {
    BookingDtoStat getBookingsStat(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> types);

    RoomStatisticDto getRoomStatisticById(LocalDateTime startDateTime,
                                          LocalDateTime endDateTime,
                                          Long roomId);

    EngineerStatisticDto getEngineerStatisticById(LocalDateTime startDateTime,
                                                  LocalDateTime endDateTime,
                                                  Long engineerId);

    PeakHoursDto getMostPopularHours(LocalDateTime startDateTime,
                                     LocalDateTime endDateTime);

    EventStat saveEventStat(EventStatDto eventStatDto);
}
