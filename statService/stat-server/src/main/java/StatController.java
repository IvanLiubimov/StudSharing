import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.EventStat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatController {

    final StatService statService;

    @PostMapping("/events")
    public ResponseEntity<EventStat> saveEvent(@RequestBody EventStatDto eventStatDto){
        log.info("получен запрос на сохранение данных события");
        return ResponseEntity.ok(statService.saveEventStat(eventStatDto));
    }

    @GetMapping(path = "/stats/bookings")
    public ResponseEntity<BookingDtoStat> getBookingsStat(@RequestParam String start,
                                                                      @RequestParam String end) {
        log.info("получен запрос на получение данных статистики бронирований");

        List<String> types = List.of("BOOKING_CREATED", "BOOKING_CANCELLED");

        String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(decodedStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(decodedEnd, formatter);

       BookingDtoStat result = statService.getBookingsStat(
                startDateTime,
                endDateTime,
                types != null ? types : List.of());

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/stats/rooms/{roomId}")
    public ResponseEntity<RoomStatisticDto> getRoomStatisticById(@PathVariable Long roomId,
                                                                 @RequestParam String start,
                                                                 @RequestParam String end) {
        log.info("получен запрос на получение данных статистики комнаты с id = {}", roomId);
        String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(decodedStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(decodedEnd, formatter);

        return ResponseEntity.ok(statService.getRoomStatisticById(startDateTime, endDateTime, roomId));
    }

    @GetMapping(path = "/stats/engineers/{engineerId}")
    public ResponseEntity<EngineerStatisticDto> getEngineerStatisticById(@PathVariable Long engineerId,
                                                                         @RequestParam String start,
                                                                         @RequestParam String end) {
        log.info("получен запрос на получение данных статистики инженера с id = {}", engineerId);

        String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(decodedStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(decodedEnd, formatter);

        return ResponseEntity.ok(statService.getEngineerStatisticById(startDateTime, endDateTime, engineerId));
    }

    @GetMapping(path = "/stats/peak-hours")
    public ResponseEntity<PeakHoursDto> getMostPopularHours(@RequestParam String start,
                                                            @RequestParam String end) {
        log.info("получен запрос на получение самых популярных часов посещения");

        String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(decodedStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(decodedEnd, formatter);

        return ResponseEntity.ok(statService.getMostPopularHours(startDateTime,
                endDateTime));
    }

    @GetMapping(path = "/stats/summary")
    public ResponseEntity<StatSummaryDto> getStatSummary(@RequestParam String start,
                                                         @RequestParam String end) {
        log.info("получен запрос на получение общих данных статистики ");

        String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(decodedStart, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(decodedEnd, formatter);

        return ResponseEntity.ok(statService.getStatSummary(startDateTime, endDateTime));
    }

}
