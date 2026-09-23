package com.kaueadriano.novici.dto;

import com.kaueadriano.novici.enums.Justification;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GoalResponse {
    private Integer id;
    private Integer userId;
    private Justification justification;
    private LocalDate startDate;
    private LocalDate endDate;
    private String motivationMessage;
    private List<EvaluationResponse> evaluations;

    public GoalResponse(Integer id, Integer userId, Justification justification, LocalDate startDate, LocalDate endDate, String motivationMessage, List<EvaluationResponse> evaluations){
        this.id = id;
        this.userId = userId;
        this.justification = justification;
        this.startDate = startDate;
        this.endDate = endDate;
        this.motivationMessage = motivationMessage;
        this.evaluations = evaluations;
    }
}
