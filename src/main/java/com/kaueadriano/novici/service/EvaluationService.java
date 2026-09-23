package com.kaueadriano.novici.service;

import com.kaueadriano.novici.dto.EvaluationRequest;
import com.kaueadriano.novici.dto.EvaluationResponse;
import com.kaueadriano.novici.exception.*;
import com.kaueadriano.novici.model.Evaluation;
import com.kaueadriano.novici.model.Goal;
import com.kaueadriano.novici.repository.EvaluationRepository;
import com.kaueadriano.novici.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private GoalRepository goalRepository;

    public List<EvaluationResponse> listAll(){
        List<Evaluation> evaluations = evaluationRepository.findAll();
        return evaluations.stream().map(evaluation -> new EvaluationResponse(
                evaluation.getId(),
                evaluation.getGoal().getId(),
                evaluation.getDone(),
                evaluation.getCommentary(),
                evaluation.getEvaluationDay()))
                .toList();
    }
    public EvaluationResponse listOne(Integer id){
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new EvaluationNotFoundException("Evaluation not found"));

        return new EvaluationResponse(
                evaluation.getId(),
                evaluation.getGoal().getId(),
                evaluation.getDone(),
                evaluation.getCommentary(),
                evaluation.getEvaluationDay()
        );
    }
    public EvaluationResponse create(EvaluationRequest evaluationRequest){
        Goal goal = goalRepository.findById(evaluationRequest.getGoalId()).orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        LocalDate requestDay = LocalDate.now();

        var isEvaluationAtTheRange = (goal.getStartDate().isEqual(requestDay) || goal.getStartDate().isBefore(requestDay))
                && (goal.getEndDate().isEqual(requestDay) || goal.getEndDate().isAfter(requestDay));

        if(!isEvaluationAtTheRange)
            throw new EvaluationDayOutRangeException("Evaluation day is outside the goal range");

        // Avaliações feitas no mesmo dia são bloqueadas
        List<Evaluation> evaluations = evaluationRepository.findAll();
        var evaluationDayAlreadyDone = evaluations.stream().anyMatch(evaluation -> evaluation.getEvaluationDay().equals(requestDay));

        if(evaluationDayAlreadyDone)
            throw new EvaluationDayAlreadyDoneException("Evaluation of the day already done");

        Evaluation evaluation = new Evaluation();
        evaluation.setGoal(goal);
        evaluation.setDone(evaluationRequest.getDone());
        evaluation.setCommentary(evaluationRequest.getCommentary());
        evaluation.setEvaluationDay(requestDay);

        Evaluation evaluationSaved = evaluationRepository.save(evaluation);

        return new EvaluationResponse(
                evaluationSaved.getId(),
                evaluation.getGoal().getId(),
                evaluation.getDone(),
                evaluation.getCommentary(),
                evaluation.getEvaluationDay()
        );
    }
}
