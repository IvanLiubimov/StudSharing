package ru.practicum.room;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.room.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long roomId);
}
