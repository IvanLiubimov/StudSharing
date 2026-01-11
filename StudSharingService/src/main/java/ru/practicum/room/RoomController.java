package ru.practicum.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/api/rooms")
    public ResponseEntity<Collection<RoomDto>> getAllRooms() {
        log.info("Получен запрос на получение всех комнат");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms());
    }

    @GetMapping("/api/rooms/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        log.info("Получен запрос на получение комнаты по id = {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomById(id));
    }

    @PostMapping("/api/admin/rooms")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        log.info("Получен запрос от администратора на создание комнаты");
        RoomDto createdRoom = roomService.createRoom(roomDto);
        log.info("Комната id={} создана", createdRoom.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/api/admin/rooms/{id}")
    public ResponseEntity<RoomDto> createRoom(@PathVariable Long id) {
        log.info("Получен запрос от администратора на обновление комнаты");
        RoomDto updatedRoomDto = roomService.editRoom(id);
        log.info("Комната id={} обновлена", id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRoomDto);
    }

    @DeleteMapping("/api/admin/room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.info("Запрос от администратора на удаление комнаты по id={}", id);
        roomService.deleteRoom(id);
        log.info("Категория id={} deleted", id);
        return ResponseEntity.noContent().build();
    }

}
