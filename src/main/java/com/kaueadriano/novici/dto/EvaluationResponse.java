package com.kaueadriano.novici.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EvaluationResponse {
    private Integer id;
    private Integer goalId;
    private Boolean done;
    private String commentary;
    private LocalDate evaluationDay;

    public EvaluationResponse(Integer id, Integer goalId, Boolean done, String commentary, LocalDate evaluationDay){
        this.id = id;
        this.goalId = goalId;
        this.done = done;
        this.commentary = commentary;
        this.evaluationDay = evaluationDay;
    }
}
