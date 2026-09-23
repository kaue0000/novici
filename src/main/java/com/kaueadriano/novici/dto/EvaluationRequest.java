package com.kaueadriano.novici.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EvaluationRequest {
    @NotNull(message = "Goal is required")
    private Integer goalId;

    @NotNull(message = "Done is required")
    private Boolean done;

    @Size(max = 255, message = "Commentary must be at most 255 characters")
    private String commentary;

}
