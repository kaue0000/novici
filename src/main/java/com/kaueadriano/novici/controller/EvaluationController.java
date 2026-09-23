package com.kaueadriano.novici.controller;

import com.kaueadriano.novici.dto.ErrorMessage;
import com.kaueadriano.novici.dto.EvaluationRequest;
import com.kaueadriano.novici.dto.EvaluationResponse;
import com.kaueadriano.novici.service.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping()
    public List<EvaluationResponse> index(){
        return evaluationService.listAll();
    }
    @GetMapping("/{id}")
    public EvaluationResponse show(@PathVariable Integer id){
        return evaluationService.listOne(id);
    }
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody EvaluationRequest evaluationRequest){
        EvaluationResponse evaluationResponse = evaluationService.create(evaluationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationResponse);
    }
}
