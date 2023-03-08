package com.musala.controllers.dto;

public enum PackageStatus {
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED");

    private final String value;

    PackageStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
