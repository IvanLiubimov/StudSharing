import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EngineerStatisticDto {
    private Long engineerId;
    private Long totalBookings;
    private Long hoursWorked;
    private Double workloadPercentage;
}
