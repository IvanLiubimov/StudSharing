package ru.liubimov.statserver.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import dto.payload.BookingStat;

import java.time.Instant;


@Entity
@Table(name = "events")
@Getter
@Setter
public class EventStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "type", nullable = false)
    private String eventType;

    @Column(name = "datetime", nullable = false)
    private Instant dateTime;

    @Type(JsonType.class)
    @Column(name = "payload", columnDefinition = "jsonb")
    private BookingStat payloadJson;
}
