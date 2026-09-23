package com.kaueadriano.novici.service;

import com.kaueadriano.novici.dto.EvaluationResponse;
import com.kaueadriano.novici.dto.GoalRequest;
import com.kaueadriano.novici.dto.GoalResponse;
import com.kaueadriano.novici.exception.GoalNotFoundException;
import com.kaueadriano.novici.exception.UserNotFoundException;
import com.kaueadriano.novici.model.Goal;
import com.kaueadriano.novici.model.User;
import com.kaueadriano.novici.repository.GoalRepository;
import com.kaueadriano.novici.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<GoalResponse> listAll(){
        List<Goal> goals = goalRepository.findAll();
        return goals.stream().map(goal -> new GoalResponse(
                goal.getId(),
                goal.getUser().getId(),
                goal.getJustification(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getMotivationMessage(),
                goal.getEvaluations().stream()
                        .map(evaluation -> new EvaluationResponse(
                                evaluation.getId(),
                                evaluation.getGoal().getId(),
                                evaluation.getDone(),
                                evaluation.getCommentary(),
                                evaluation.getEvaluationDay()
                        )).toList()
                )).toList();
    }

    public GoalResponse listOne(Integer id){
        Goal goal = goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        return new GoalResponse(
                goal.getId(),
                goal.getUser().getId(),
                goal.getJustification(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getMotivationMessage(),
                goal.getEvaluations().stream()
                        .map(evaluation -> new EvaluationResponse(
                                evaluation.getId(),
                                evaluation.getGoal().getId(),
                                evaluation.getDone(),
                                evaluation.getCommentary(),
                                evaluation.getEvaluationDay()
                        )).toList()
                );
    }

    public GoalResponse create(GoalRequest goalRequest){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        Goal goal = new Goal();
        goal.setUser(user); // User resgatado da autenticação
        goal.setJustification(goalRequest.getJustification());
        goal.setStartDate(goalRequest.getStartDate());
        goal.setEndDate(goalRequest.getEndDate());
        goal.setMotivationMessage(goalRequest.getMotivationMessage());

        Goal goalSaved = goalRepository.save(goal);

        return new GoalResponse(
                goalSaved.getId(),
                goalSaved.getUser().getId(),
                goalSaved.getJustification(),
                goalSaved.getStartDate(),
                goalSaved.getEndDate(),
                goalSaved.getMotivationMessage(),
                goalSaved.getEvaluations().stream().map(evaluation -> new EvaluationResponse(
                        evaluation.getId(),
                        evaluation.getGoal().getId(),
                        evaluation.getDone(),
                        evaluation.getCommentary(),
                        evaluation.getEvaluationDay()
                )).toList()
        );
    }
}
