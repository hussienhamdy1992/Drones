package com.musala.model.entities;

import com.musala.controllers.dto.DroneStatus;
import com.musala.model.validtor.ValueOfEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "DRONE")
@Getter @Setter
public class Drone {
    @Id
    @Column(name = "DRONE_ID", nullable = false)
    private Long droneId;

    @NotNull
    @Column(name = "MODEL_ID")
    private Long modelId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MODEL_ID",updatable = false,insertable = false)
//    private Model model;

    @Max(1) @Min(0)
    @NotNull
    @Column(name = "BATTERY_CAPACITY")
    private Float batteryCapacity;

    @ValueOfEnum(enumClass = DroneStatus.class)
    @Column(name = "STATE", length = 50)
    private String state;

    @Length(min=1, max=100)
    @Column(name = "SERIAL_NUMBER", length = 100)
    private String serialNumber;

    @OneToMany(mappedBy = "drone",fetch = FetchType.LAZY)
    private List<Package> packages ;//= new LinkedHashSet<>();

}