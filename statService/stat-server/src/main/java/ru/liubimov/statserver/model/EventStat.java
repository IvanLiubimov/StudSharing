package ru.liubimov.statserver.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import dto.payload.BookingStat;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class EventStat {
    @Id
    @GeneratedValue
    private Long id;

    @Column (nullable = false)
    private String eventType;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private BookingStat payloadJson;
}
