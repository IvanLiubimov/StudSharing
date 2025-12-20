package dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class BookingDtoStat {
        private Long total;
        private Map<String, Integer> byDay;
}
