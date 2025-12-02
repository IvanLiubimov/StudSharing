package model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BookingStat {
        private Long Id;
        private Long userId;
        private Long roomId;
        private LocalDateTime dateTime;
    }

