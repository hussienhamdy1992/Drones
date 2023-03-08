package com.musala.controllers.dto;

import com.musala.model.entities.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class SearchDroneDto {
    private Long droneId;
    private Long modelId;
    private Float batteryCapacity;
    private String state;
    private String serialNumber;
}
