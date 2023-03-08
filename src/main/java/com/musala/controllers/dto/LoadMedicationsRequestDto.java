package com.musala.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class LoadMedicationsRequestDto {
    private Long droneId;
    private Set<MedicationDto> medications;
}
