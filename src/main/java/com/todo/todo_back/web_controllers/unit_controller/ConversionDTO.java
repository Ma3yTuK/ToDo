package com.todo.todo_back.web_controllers.unit_controller;

import com.todo.todo_back.entities.MeasurementUnit;
import com.todo.todo_back.entities.UnitUnitConversion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDTO {

    public ConversionDTO(UnitUnitConversion conversion) {
        id = conversion.getId();
        measurementTo = conversion.getMeasurementTo();
        coefficient = conversion.getCoefficient();
    }

    private Long id;

    private MeasurementUnit measurementTo;

    private Double coefficient;
}
