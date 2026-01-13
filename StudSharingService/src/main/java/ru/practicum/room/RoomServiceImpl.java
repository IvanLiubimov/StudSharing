package ru.practicum.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.bookings.BookingRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.room.model.Room;
import ru.practicum.room.model.RoomMapper;
import ru.practicum.room.model.RoomValidator;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomValidator roomValidator;
    private final BookingRepository bookingRepository;

    @Override
    public Collection<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(RoomMapper::toDto).toList();
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = getRoomIfExists(id);
        return RoomMapper.toDto(room);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        roomValidator.validateForCreate(roomDto);
        Room room = roomRepository.save(RoomMapper.toEntity(roomDto));
        return RoomMapper.toDto(room);
    }

    @Override
    public RoomDto editRoom(Long id, RoomDto roomDto) {
        Room oldRoom = getRoomIfExists(id);
        roomValidator.validateForUpdate(id, roomDto);
        Room updatedRoom = RoomMapper.updateEntity(oldRoom, roomDto);

        return RoomMapper.toDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new NotFoundException("Комнаты с id =" + id + " не найдена");
        }

        if (bookingRepository.existsByRoomId(id)) {
            throw new ConflictException("Нельзя удалять комнаты, в которых есть бронирования");
        }

        roomRepository.deleteById(id);
    }

    private Room getRoomIfExists(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комнаты с id = " + id + " не найдено"));
    }
}
