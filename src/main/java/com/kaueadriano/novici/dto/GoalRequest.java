package com.kaueadriano.novici.dto;

import com.kaueadriano.novici.enums.Justification;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalRequest {
    @NotNull(message = "Justification is required")
    private Justification justification;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Size(max = 255, message = "Motivation message must be at most 255 characters")
    private String motivationMessage;
}
