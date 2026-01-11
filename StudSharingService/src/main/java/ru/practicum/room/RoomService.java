package ru.practicum.room;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface RoomService {
    Collection<RoomDto> getAllRooms();

    RoomDto getRoomById(Long id);

    RoomDto createRoom(RoomDto roomDto);

    RoomDto editRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);
}
