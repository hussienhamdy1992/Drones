package com.musala.model.repositories;

import com.musala.controllers.dto.DroneStatus;
import com.musala.controllers.dto.SearchDroneDto;
import com.musala.model.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {



    @Query(value = "select " +
            "new com.musala.controllers.dto.SearchDroneDto(" +
            "drone.droneId\n" +
            ",drone.modelId\n" +
            ",drone.batteryCapacity\n" +
            ",drone.state\n" +
            ",drone.serialNumber" +
            " )" +
            "from Drone drone where drone.state =:status")
    public List<SearchDroneDto> getAvailableDrones(String status );
}