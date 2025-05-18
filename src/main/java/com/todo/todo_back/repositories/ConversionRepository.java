package com.todo.todo_back.repositories;

import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.UnitUnitConversion;
import io.opencensus.stats.Measurement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.*;
import java.awt.print.Pageable;
import java.util.Collection;

public interface ConversionRepository extends JpaRepository<UnitUnitConversion, Long>  {
    Collection<UnitUnitConversion> findAllByMeasurementFrom(MeasurementUnit from, Sort sort);
}
