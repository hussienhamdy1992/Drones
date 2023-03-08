package com.musala.controllers.dto;

import com.musala.model.entities.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter @Setter @AllArgsConstructor
public class MedicationDto {
    private Long id;

    private String medicationName;

    private Long weight;

    private String code;

    private byte[] image;


}