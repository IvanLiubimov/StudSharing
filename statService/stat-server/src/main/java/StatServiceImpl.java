import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import model.BookingStat;
import model.EventStat;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    final Validator validator;
    final EventMapper eventMapper;
    final StatRepository statRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EventStat saveEventStat(EventStatDto eventStatDto) {
        validator.dtoValidate(eventStatDto);
        EventStat eventStat = eventMapper.toEntity(eventStatDto);
        return statRepository.save(eventStat);
    }

    @Override
    public BookingDtoStat getBookingsStat(LocalDateTime startDateTime,
                                                      LocalDateTime endDateTime,
                                                      List<String> types) {
        validator.requestValidator(startDateTime, endDateTime);
        Instant start = toInstant(startDateTime);
        Instant end = toInstant(endDateTime);

        Collection<EventStat> eventStats = statRepository.findAllByTypes(start, end, types);

        Collection<BookingStat> listOfBookings = eventStats
                .stream()
                .map(eventStat -> {
                    try {
                        return objectMapper.readValue(eventStat.getPayloadJson(), BookingStat.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        BookingDtoStat bookingDtoStat = new BookingDtoStat();
        Map<String, Integer> byDay = new HashMap<>();
                for (BookingStat booking : listOfBookings) {
            String date = booking.getDateTime().toLocalDate().toString();
            byDay.put(date, byDay.getOrDefault(date, 0) + 1);
        }
        bookingDtoStat.setTotal((long) listOfBookings.size());
        bookingDtoStat.setByDay(byDay);

        return bookingDtoStat;
    }

    @Override
    public RoomStatisticDto getRoomStatisticById(Long roomId) {
        return null;
    }

    @Override
    public EngineerStatisticDto getEngineerStatisticById(Long engineerId) {
        return null;
    }

    @Override
    public PeakHoursDto getMostPopularHours() {
        return null;
    }

    @Override
    public StatSummaryDto getStatSummary() {
        return null;
    }

    private Instant toInstant (LocalDateTime localDateTime) {
        return localDateTime != null
                ? localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                : null;
    }
}


