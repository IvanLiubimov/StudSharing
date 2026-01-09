package ru.liubimov.statserver.Service;

import ru.liubimov.statserver.Repository.StatRepository;
import dto.*;
import ru.liubimov.statserver.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import dto.payload.BookingStat;
import ru.liubimov.statserver.model.EventMapper;
import ru.liubimov.statserver.model.EventStat;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    final Validator validator;
    final EventMapper eventMapper;
    final StatRepository statRepository;

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

        Collection<EventStat> eventStats = statRepository.findAllByEventTypes(start, end, types);

        Collection<BookingStat> bookingStats = getBookingStats(eventStats);

        BookingDtoStat bookingDtoStat = new BookingDtoStat();
        Map<String, Integer> byDay = new HashMap<>();
                for (BookingStat booking : bookingStats) {
            String date = booking.getStartTime().toLocalDate().toString();
            byDay.put(date, byDay.getOrDefault(date, 0) + 1);
        }
        bookingDtoStat.setTotal((long) bookingStats.size());
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

        Collection<BookingStat> bookingStats = getBookingStats(eventStats);

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

        Collection<BookingStat> bookingStats = getBookingStats(eventStats);

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

        Instant start = toInstant(startDateTime);
        Instant end = toInstant(endDateTime);

        Collection<EventStat> eventStats = statRepository.getStats(start, end).stream()
                .filter(e -> e.getEventType().equals("BOOKING_CREATED")).toList();

        Collection<BookingStat> bookingStats = getBookingStats(eventStats);

        //таблица часов и их частоты встречаемости
        Map<Integer, Long> freqHours = new HashMap<>();

        //заполнение этой таблицы
        for (BookingStat bookingStat : bookingStats) {
            List<Integer> hours = expandHours(bookingStat.getStartTime().getHour(),
            bookingStat.getEndTime().getHour());
            for (int h : hours) {
                freqHours.merge(h, 1L, Long::sum);
            }
        }

        //поиск максимальной частоты встречаемости часа
        Long maxFreq = freqHours.values()
                .stream()
                .mapToLong(v -> v)
                .max()
                .orElse(0);

        PeakHoursDto peakHoursDto = new PeakHoursDto();

        //создаём дто с нужными часами
        peakHoursDto.setPeakHours(freqHours.entrySet().stream()
                .filter(h -> h.getValue().equals(maxFreq))
                .map(e -> e.getKey())
                .collect(Collectors.toSet()));

        return peakHoursDto;
    }

    private Instant toInstant (LocalDateTime localDateTime) {
        return localDateTime != null
                ? localDateTime.atZone(ZoneId.systemDefault()).toInstant()
                : null;
    }

    private List<Integer> expandHours(int startHour, int endHour) {
        List<Integer> hours = new ArrayList<>();

        int h = startHour;

        // идём час за часом, двигаясь по кругу 24 часа
        while (h != endHour) {
            hours.add(h);
            h = (h + 1) % 24; // после 23 → 0
        }

        return hours; // endHour НЕ включаем — так работает диапазон в статистике
    }

    private Collection<BookingStat> getBookingStats(Collection<EventStat> eventStats) {
        return eventStats.stream()
                .map(EventStat::getPayloadJson)
                .toList();
    }


}


