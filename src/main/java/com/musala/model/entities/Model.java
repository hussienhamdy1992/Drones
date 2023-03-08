package com.musala.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODEL")
public class Model {
    @Id
    @Column(name = "MODEL_ID", nullable = false)
    private Long id;

    @Column(name = "MODEL_NAME", length = 1)
    private String modelName;

    @Column(name = "WEIGHT_LIMIT")
    private Float weightLimit;

    public Float getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Float weightLimit) {
        this.weightLimit = weightLimit;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //TODO Reverse Engineering! Migrate other columns to the entity
}