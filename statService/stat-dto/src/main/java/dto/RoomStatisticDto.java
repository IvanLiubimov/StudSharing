package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomStatisticDto {
    private Long roomId;
    private Long totalBookings;
    private Long hoursBooked;
    private Double occupancyRate;
}
