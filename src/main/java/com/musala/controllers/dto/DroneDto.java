package com.musala.controllers.dto;

import com.musala.model.entities.Model;
import com.musala.model.entities.Package;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter @Setter
public class DroneDto {

    private Long droneId;
    private Long modelId;
    private Model model;
    private Float batteryCapacity;
    private String state;
    private String serialNumber;
    private PackageDto packages;// = new LinkedHashSet<>();
}
