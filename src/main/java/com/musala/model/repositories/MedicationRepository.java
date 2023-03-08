package com.musala.model.repositories;

import com.musala.controllers.dto.MedicationDto;
import com.musala.model.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(value = "select new com.musala.controllers.dto.MedicationDto(\n" +
            "med.medicationId\n" +
            ",med.medicationName\n" +
            ",med.weight\n" +
            ",med.code\n" +
            ",med.image " +
            ")\n" +
            "from Package pack left join Medication med\n" +
            "on pack.packageId = med.packageId \n" +
            "where pack.packageStatus ='LOADED'\n" +
            "and pack.droneId =:droneId")
    public List<MedicationDto> getLoadedMedications(Long droneId);
}