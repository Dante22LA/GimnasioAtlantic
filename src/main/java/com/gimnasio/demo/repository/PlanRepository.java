package com.gimnasio.demo.repository;

import com.gimnasio.demo.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
}
