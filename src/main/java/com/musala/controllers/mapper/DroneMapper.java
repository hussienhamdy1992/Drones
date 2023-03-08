package com.musala.controllers.mapper;

import com.musala.controllers.dto.DroneDto;
import com.musala.controllers.dto.RegisterDroneRequestDto;
import com.musala.model.entities.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper( componentModel = "spring")
public interface DroneMapper {

//    DroneDto mapDroneToDroneDto(DroneDto droneDto);
//    Drone mapDroneDtoToDrone(DroneDto droneDto);
    Drone mapRegisterDroneRequestDtoToDrone(RegisterDroneRequestDto registerDroneRequestDto);


//    List<DroneDto> mapDroneListToDroneDtoList(List<Drone> drone);
}
