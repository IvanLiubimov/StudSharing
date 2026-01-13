package ru.practicum.room.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.exception.ValidationException;
import ru.practicum.room.RoomDto;
import ru.practicum.room.RoomRepository;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;

    public void validateForCreate(RoomDto dto) {
        validateCommon(dto);

        if (roomRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ValidationException("Комната с таким именем уже существует");
        }
    }

    public void validateForUpdate(Long roomId, RoomDto dto) {
        validateCommon(dto);

        if (dto.getName() != null &&
                roomRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), roomId)) {
            throw new ValidationException("Комната с таким именем уже существует");
        }
    }

    private void validateCommon(RoomDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ValidationException("Название комнаты не может быть пустым");
        }

        if (dto.getName().length() < 3 || dto.getName().length() > 100) {
            throw new ValidationException("Название комнаты должно быть от 3 до 100 символов");
        }

        if (dto.getCapacity() == null || dto.getCapacity() <= 0) {
            throw new ValidationException("Вместимость должна быть больше 0");
        }

        if (dto.getDescription() != null && dto.getDescription().length() > 500) {
            throw new ValidationException("Описание не может превышать 500 символов");
        }
    }
}

