package ru.practicum.engineer.model;

import org.springframework.util.StringUtils;
import ru.practicum.engineer.EngineerDto;

public class EngineerValidator {

    public static void validateForCreate(EngineerDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Engineer body is required");
        }

        validateCommon(dto);
    }

    public static void validateForUpdate(EngineerDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Engineer body is required");
        }

        validateCommon(dto);
    }

    private static void validateCommon(EngineerDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new IllegalArgumentException("Engineer name must not be empty");
        }

        if (dto.getName().length() > 100) {
            throw new IllegalArgumentException("Engineer name is too long (max 100 chars)");
        }

        if (!StringUtils.hasText(dto.getSpecialization())) {
            throw new IllegalArgumentException("Engineer specialization must not be empty");
        }

        if (dto.getSpecialization().length() > 100) {
            throw new IllegalArgumentException("Engineer specialization is too long (max 100 chars)");
        }

        if (StringUtils.hasText(dto.getCredits()) && dto.getCredits().length() > 255) {
            throw new IllegalArgumentException("Engineer credits is too long (max 255 chars)");
        }
    }
}

