package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<MeasurementUnit, Long> {
}
