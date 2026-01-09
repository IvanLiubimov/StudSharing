package ru.liubimov.statserver.Repository;

import ru.liubimov.statserver.model.EventStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<EventStat, Long> {

    @Query("SELECT e FROM EventStat e WHERE e.dateTime BETWEEN :start AND :end")
    public Collection<EventStat> getStats(Instant start, Instant end);


    @Query("SELECT e FROM EventStat e WHERE e.dateTime BETWEEN :start AND :end AND e.eventType IN :types")
    public Collection<EventStat> findAllByEventTypes(Instant start, Instant end, List<String> types);

    @Query(value = "SELECT * FROM events " +
            "WHERE dateTime BETWEEN :start AND :end " +
            "AND payload ->>'roomId' = :roomId",
            nativeQuery = true)
    public Collection<EventStat> findAllByRoomId(Instant start, Instant end, String roomId);

    @Query(value = "SELECT * FROM events " +
            "WHERE payload ->>'engineerId' = :engineerId " +
            "AND datetime BETWEEN :start AND :end", nativeQuery = true)
    public Collection<EventStat> findAllByEngineerId(Instant start, Instant end, String engineerId);
}
