
import dto.EventStatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class StatClient {
    private final String statServerUrl;
    private final RestTemplate restTemplate;


    public StatClient(StatClientConfig config, RestTemplate restTemplate) {
        this.statServerUrl = config.getUrl();
        this.restTemplate = restTemplate;
    }

    public void saveEvent(EventStatDto eventStatDto) {
        log.info("Отправка EVENT в статистику: {}", eventStatDto);
        restTemplate.postForEntity(statServerUrl + "/events", eventStatDto, Void.class);
    }
}
