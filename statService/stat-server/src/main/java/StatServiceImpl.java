import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import model.BookingStat;
import model.EventStat;
import org.springframework.stereotype.Service;

import java.time.*;
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
            String date = booking.getStartTime().toLocalDate().toString();
            byDay.put(date, byDay.getOrDefault(date, 0) + 1);
        }
        bookingDtoStat.setTotal((long) listOfBookings.size());
        bookingDtoStat.setByDay(byDay);

        return bookingDtoStat;
    }

    @Override
    public RoomStatisticDto getRoomStatisticById(LocalDateTime startDateTime,
                                                 LocalDateTime endDateTime,
                                                 Long roomId) {

        Instant start = toInstant(startDateTime);
        Instant end = toInstant(endDateTime);

        Collection<EventStat> eventStats = statRepository.findAllByRoomId(start, end, String.valueOf(roomId));

        Collection<BookingStat> bookingStats = eventStats.stream()
                .map(eventStat -> {
                    try {
                        return objectMapper.readValue(eventStat.getPayloadJson(), BookingStat.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        if (bookingStats.isEmpty()) {
            throw new NotFoundException("No statistics found for room " + roomId);
        }

        RoomStatisticDto roomStatisticDto = new RoomStatisticDto();

        roomStatisticDto.setRoomId(roomId);
        roomStatisticDto.setTotalBookings((long) bookingStats.size());
        long hoursBooked = bookingStats.stream()
                .mapToLong(bookingStat -> Duration.between(bookingStat.getStartTime(), bookingStat.getEndTime()).toHours())
                .sum();
        roomStatisticDto.setHoursBooked(hoursBooked);
        roomStatisticDto.setOccupancyRate((double) hoursBooked/Duration.between(start, end).toHours() * 100);

        return roomStatisticDto;
    }

    @Override
    public EngineerStatisticDto getEngineerStatisticById(LocalDateTime startDateTime,
                                                         LocalDateTime endDateTime,
                                                         Long engineerId) {

        Instant start = toInstant(startDateTime);
        Instant end = toInstant(endDateTime);

        Collection<EventStat> eventStats = statRepository.findAllByEngineerId(start, end, String.valueOf(engineerId));

        Collection<BookingStat> bookingStats = eventStats.stream()
                .map(eventStat -> {
                    try {
                        return objectMapper.readValue(eventStat.getPayloadJson(), BookingStat.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        if (bookingStats.isEmpty()) {
            throw new NotFoundException("No statistics found for engineer " + engineerId);
        }

        EngineerStatisticDto engineerStatisticDto = new EngineerStatisticDto();

        engineerStatisticDto.setEngineerId(engineerId);
        engineerStatisticDto.setTotalBookings((long) bookingStats.size());
        long hoursWorked = bookingStats.stream()
                .mapToLong(bookingStat -> Duration.between(bookingStat.getStartTime(), bookingStat.getEndTime()).toHours())
                .sum();
        engineerStatisticDto.setHoursWorked(hoursWorked);

        return engineerStatisticDto;
    }

    @Override
    public PeakHoursDto getMostPopularHours(LocalDateTime startDateTime,
                                            LocalDateTime endDateTime) {


        return null;
    }

    @Override
    public StatSummaryDto getStatSummary(LocalDateTime startDateTime,
                                         LocalDateTime endDateTime) {
        return null;
    }

    private Instant toInstant (LocalDateTime localDateTime) {
        return localDateTime != null
                ? localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                : null;
    }
}


