package ru.practicum.engineer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EngineerDto {
    private Long id;
    private String name;
    private String credits;
    private String specialization;
}
