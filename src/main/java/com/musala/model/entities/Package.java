package com.musala.model.entities;

import com.musala.controllers.dto.PackageStatus;
import com.musala.model.validtor.PackageMaxWeight;
import com.musala.model.validtor.ValueOfEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name ="Package" )
@Table(name = "PACKAGES")
@Getter @Setter
public class Package {
    @Id
    @Column(name = "PACKAGE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    @NotNull
    @Column(name = "DRONE_ID")
    private Long droneId;

    @ValueOfEnum(enumClass = PackageStatus.class)
    @Column(name = "PACKAGE_STATUS", length = 50)
    private String packageStatus;

    @ManyToOne()
    @JoinColumn(name ="DRONE_ID" ,insertable = false,updatable = false)
    private Drone drone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="PACKAGE_ID")
    private Set<Medication> medications = new LinkedHashSet<>();

    //TODO Reverse Engineering! Migrate other columns to the entity
}