package com.musala.controllers.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PackageDto implements Serializable {
    private  Long packageId;
    private  Long droneId;
    private  String packageStatus;
}
