import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class EventStatDto {
    private String type;
    private LocalDateTime timestamp;
    private Map<String, Object> payload;
}
