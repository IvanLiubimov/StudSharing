package ru.practicum.bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.bookings.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoomId(Long id);

    boolean existsByEngineerId(Long id);
}
