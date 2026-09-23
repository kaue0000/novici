package com.kaueadriano.novici.controller;

import com.kaueadriano.novici.dto.ErrorMessage;
import com.kaueadriano.novici.dto.GoalRequest;
import com.kaueadriano.novici.dto.GoalResponse;
import com.kaueadriano.novici.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {
    @Autowired
    private GoalService goalService;

    @GetMapping()
    public List<GoalResponse> index(){
        return goalService.listAll();
    }
    @GetMapping("/{id}")
    public GoalResponse show(@PathVariable Integer id) {
        return goalService.listOne(id);
    }
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody GoalRequest goalRequest){
        GoalResponse newGoal = goalService.create(goalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGoal);
    }
}
