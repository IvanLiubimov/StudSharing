package ru.practicum.room.model;

import org.springframework.stereotype.Component;
import ru.practicum.room.RoomDto;

@Component
public class RoomMapper {

    public Room toEntity(RoomDto dto) {
        if (dto == null) {
            return null;
        }

        Room room = new Room();
        room.setId(dto.getId());
        room.setName(dto.getName());
        room.setDescription(dto.getDescription());
        room.setCapacity(dto.getCapacity());
        return room;
    }

    public RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }

        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setCapacity(room.getCapacity());
        return dto;
    }

    public void updateEntity(Room room, RoomDto dto) {
        if (dto.getName() != null) {
            room.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            room.setDescription(dto.getDescription());
        }
        if (dto.getCapacity() != null) {
            room.setCapacity(dto.getCapacity());
        }
    }
}

