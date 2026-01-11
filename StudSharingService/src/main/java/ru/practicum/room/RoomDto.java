package ru.practicum.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private Long capacity;
}

