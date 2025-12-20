package ru.liubimov.statserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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

    @Column(columnDefinition = "jsonb")
    private String payloadJson;
}
