package com.musala.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "MEDICATION")
public class Medication {
    @Id
    @Column(name = "MEDICATINO_ID", nullable = false)
    private Long medicationId;

    @Pattern(regexp = "([A-Za-z0-9]|-|_)+")
    @Column(name = "MEDICATION_NAME", length = 200)
    private String medicationName;

    @Column(name = "WEIGHT")
    private Long weight;

    @Pattern(regexp = "([A-Z0-9]|_)+")
    @Column(name = "CODE", length = 200)
    private String code;

    @Column(name = "IMAGE")
    private byte[] image;

    @Column(name="PACKAGE_ID")
    private Long packageId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PACKAGE_ID")
//    private Package _package;
//
//    public Package get_package() {
//        return _package;
//    }
//
//    public void set_package(Package _package) {
//        this._package = _package;
//    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return medicationId.equals(that.medicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationId);
    }
}