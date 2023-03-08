package com.musala.controllers.dto;

public enum DroneStatus {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String value;

    DroneStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
