package ru.practicum.engineer.model;

import ru.practicum.engineer.EngineerDto;

public class EngineerMapper {

    public static EngineerDto toDto(Engineer engineer) {
        if (engineer == null) {
            return null;
        }

        EngineerDto dto = new EngineerDto();
        dto.setId(engineer.getId());
        dto.setName(engineer.getName());
        dto.setSpecialization(engineer.getSpecialization());
        dto.setCredits(engineer.getCredits());
        return dto;
    }

    public static Engineer toEntity(EngineerDto dto) {
        if (dto == null) {
            return null;
        }

        Engineer engineer = new Engineer();
        engineer.setId(dto.getId()); // важно для update
        engineer.setName(dto.getName());
        engineer.setSpecialization(dto.getSpecialization());
        engineer.setCredits(dto.getCredits());
        return engineer;
    }

    public static void updateEntity(Engineer engineer, EngineerDto dto) {
        if (dto == null || engineer == null) {
            return;
        }

        engineer.setName(dto.getName());
        engineer.setSpecialization(dto.getSpecialization());
        engineer.setCredits(dto.getCredits());
    }
}

