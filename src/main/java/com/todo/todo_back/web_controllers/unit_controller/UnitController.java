package com.todo.todo_back.web_controllers.unit_controller;

import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.UnitUnitConversion;
import com.todo.todo_back.repositories.ConversionRepository;
import com.todo.todo_back.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("units")
public class UnitController {
    private final UnitRepository unitRepository;
    private final ConversionRepository conversionRepository;

    @GetMapping("/all")
    public Collection<ConversionDTO> getConversions(@RequestParam Long measurementUnitId) {
        MeasurementUnit unit = unitRepository.findById(measurementUnitId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return conversionRepository.findAllByMeasurementFrom(unit, Sort.by(String.join("."), UnitUnitConversion.Fields.MEASUREMENT_TO.getDatabaseFieldName(), MeasurementUnit.Fields.NAME.getDatabaseFieldName())).stream().map(ConversionDTO::new).collect(Collectors.toList());
    }
}
