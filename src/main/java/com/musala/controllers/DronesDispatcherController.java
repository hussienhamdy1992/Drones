package com.musala.controllers;

import com.musala.controllers.dto.*;
import com.musala.model.services.impl.DronesDispatcherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "dispatch/")
public class DronesDispatcherController {

    @Autowired
    private DronesDispatcherServiceImpl dronesDispatcherService;

    @PostMapping(path = "registerDrone")
    public void registerDrone(@RequestBody RegisterDroneRequestDto registerDroneRequestDto){
        dronesDispatcherService.registerDrone(registerDroneRequestDto);
    }

    @GetMapping(path = "getAvailableDrones")
    public List<SearchDroneDto> getAvailableDrones(){
        return dronesDispatcherService.getAvailableDrones();
    }

    @GetMapping(path = "getDroneBatteryLevel/{droneId}")
    public Float getDroneBatteryLevel(@PathVariable(name = "droneId") Long droneId){
        return dronesDispatcherService.getDroneBatteryLevel(droneId);

    }

    @PutMapping(path = "loadDroneWithMedications")
    public void loadDroneWithMedications(@RequestBody LoadMedicationsRequestDto loadMedicationsRequestDto){
        dronesDispatcherService.loadDroneWithMedications(loadMedicationsRequestDto);
    }

    @GetMapping(path = "getDroneLoadedMedications/{droneId}")
    public List<MedicationDto> getDroneLoadedMedications(@PathVariable Long droneId){

        return dronesDispatcherService.getLoadedMedications(droneId);
    }

    @GetMapping(path = "checkingDroneLoadedMedications/{droneId}")
    public void checkingDroneLoadedMedications(@PathVariable Long droneId){
        dronesDispatcherService.checkingDroneLoadedMedications(droneId);

    }
}
