package ru.practicum.engineer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.bookings.BookingRepository;
import ru.practicum.engineer.model.Engineer;
import ru.practicum.engineer.model.EngineerMapper;
import ru.practicum.engineer.model.EngineerValidator;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EngineerServiceImpl implements EngineerService {

    private final EngineerRepository engineerRepository;
    private final BookingRepository bookingRepository;


    @Override
    public Collection<EngineerDto> getAllEngineers() {
        Collection<Engineer> listOfEngineers = engineerRepository.findAll();
        return listOfEngineers.stream().map(EngineerMapper::toDto).toList();
    }

    @Override
    public EngineerDto getEngineerById(Long id) {
        Engineer engineer = engineerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Engineer with id=" + id + " not found"));

        return EngineerMapper.toDto(engineer);
    }

    @Override
    public EngineerDto createEngineer(EngineerDto engineerDto) {
        EngineerValidator.validateForCreate(engineerDto);

        Engineer engineer = EngineerMapper.toEntity(engineerDto);
        Engineer saved = engineerRepository.save(engineer);

        return EngineerMapper.toDto(saved);
    }

    @Override
    public EngineerDto editEngineer(Long id, EngineerDto engineerDto) {
        EngineerValidator.validateForUpdate(engineerDto);

        Engineer engineer = engineerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Engineer with id=" + id + " not found"));

        EngineerMapper.updateEntity(engineer, engineerDto);

        Engineer updated = engineerRepository.save(engineer);
        return EngineerMapper.toDto(updated);
    }

    @Override
    public void deleteEngineer(Long id) {
        if (!engineerRepository.existsById(id)) {
            throw new IllegalArgumentException("Engineer with id=" + id + " not found");
        }

        boolean hasBookings = bookingRepository.existsByEngineerId(id);
        if (hasBookings) {
            throw new IllegalStateException("Cannot delete engineer with active bookings");
        }

        engineerRepository.deleteById(id);
    }
}
