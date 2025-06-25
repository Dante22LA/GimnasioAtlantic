package com.gimnasio.demo.controller;

import com.gimnasio.demo.model.Plan;
import com.gimnasio.demo.repository.PlanRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "*")
public class PlanController {

    private final PlanRepository repo;

    public PlanController(PlanRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Plan> obtenerPlanes() {
        return repo.findAll();
    }

}
