import model.BookingStat;
import model.EventStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface StatRepository extends JpaRepository<EventStat, Long> {

    @Query("SELECT e FROM EVENTS WHERE e.datetime BETWEEN :start AND :end")
    public Collection<BookingStat> getStats(Instant start, Instant end);

    @Query("SELECT e FROM EventStat WHERE e.datetime BETWEEN :start AND :end AND e.type IN :types")
    public Collection<EventStat> findAllByTypes(Instant start, Instant end, List<String> types);
}
