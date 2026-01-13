package ru.practicum.engineer;

import org.springframework.stereotype.Service;
import ru.practicum.room.RoomDto;

import java.util.Collection;

@Service
public interface EngineerService {
    Collection<EngineerDto> getAllEngineers();

    EngineerDto getEngineerById(Long id);

    EngineerDto createEngineer(EngineerDto engineerDto);

    EngineerDto editEngineer(Long id, EngineerDto engineerDto);

    void deleteEngineer(Long id);
}
