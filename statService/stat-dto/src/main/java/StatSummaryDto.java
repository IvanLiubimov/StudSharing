import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatSummaryDto {
    private Long totalBookings;
    private Long totalUsers;
    private Long totalRooms;
    private Long totalEngineers;
    private List<Integer> peakHours;
    private Long mostPopularRoom;
    private Long mostActiveEngineer;
}
