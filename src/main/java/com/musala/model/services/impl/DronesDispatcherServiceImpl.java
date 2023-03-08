package com.musala.model.services.impl;

import com.musala.controllers.dto.*;
import com.musala.controllers.mapper.DroneMapper;
import com.musala.model.entities.Drone;
import com.musala.model.entities.Medication;
import com.musala.model.entities.Model;
import com.musala.model.entities.Package;
import com.musala.model.repositories.DroneRepository;
import com.musala.model.repositories.MedicationRepository;
import com.musala.model.repositories.ModelRepository;
import com.musala.model.repositories.PackageRepository;
import com.musala.utils.JavaxValidator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@Service
public class DronesDispatcherServiceImpl {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private JavaxValidator<Drone> droneValidator;

    @Autowired
    private JavaxValidator<Medication> medicationValidator;

    @Autowired
    private JavaxValidator<Package> packageValidator;


    public void registerDrone(RegisterDroneRequestDto registerDroneRequestDto){

        Drone drone = droneMapper.mapRegisterDroneRequestDtoToDrone(registerDroneRequestDto);
//        drone.setId(null);

        droneValidator.validate(drone);

        droneRepository.save(drone);
    }

    public List<SearchDroneDto> getAvailableDrones(){

        List<SearchDroneDto> all = droneRepository.getAvailableDrones(DroneStatus.IDLE.getValue());

        return all;

    }

    public Float getDroneBatteryLevel(Long droneId){

        Optional<Drone> byId = droneRepository.findById(droneId);
        Drone drone = byId.get();
        return drone.getBatteryCapacity();

    }


    public List<MedicationDto> getLoadedMedications(Long droneId){
        List<MedicationDto> all = medicationRepository.getLoadedMedications(droneId);
        return all;
    }

    @Transactional
    @SneakyThrows
    public void checkingDroneLoadedMedications(Long droneId){
        Package pack = new Package();
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new Exception("Drone not found"));

        Integer index = getLoadingPackageIndex(drone.getPackages());

        if(index!=null){
            pack = drone.getPackages().get(index);
        }else {
            pack = new Package();
        }

        pack.getMedications().forEach((medication -> {medicationValidator.validate(medication);}));

        packageValidator.validate(pack);
        this.getAndValidateModleMaxWeight(drone.getModelId(),pack.getMedications());

    }


    public boolean validateMaxWeight(Float maxWeight,Set<Medication> medicationSet) {
        Float sum = 0F;
        if(medicationSet!=null&&maxWeight!=null){
            for(Medication m : medicationSet){
                sum += m.getWeight();
            }
        }
        boolean result=sum <= maxWeight ;
        return result;
    }

    public void getAndValidateModleMaxWeight(Long modelId,Set<Medication> medicationSet) throws Exception {
        Model droneModel = modelRepository.findById(modelId).orElseThrow(
                () -> new Exception("Drone Model not found")
        );
        Boolean isValidWeight = validateMaxWeight(droneModel.getWeightLimit(),medicationSet);
        if(!isValidWeight){
            throw  new Exception("Exceed Maximum weight");
        }
    }

    public void validateDroneAvailability(String state) throws Exception {
        if(state==null ||
                state.equals(DroneStatus.LOADED.getValue())||
                state.equals(DroneStatus.DELIVERING.getValue())||
                state.equals(DroneStatus.DELIVERED.getValue())||
                state.equals(DroneStatus.RETURNING.getValue())
        ){
            throw new Exception("Drone is not availble for loading");
        }
    }

    @Transactional
    @SneakyThrows
    public void loadDroneWithMedications(LoadMedicationsRequestDto loadMedicationsRequestDto){
        Package pack = new Package();
        Drone drone = droneRepository.findById(loadMedicationsRequestDto.getDroneId())
                .orElseThrow(() -> new Exception("Drone not found"));

        this.validateDroneAvailability(drone.getState());

        if(drone.getBatteryCapacity() < 0.25){
            throw new Exception("Drone low Battery");
        }

        drone.setState(DroneStatus.LOADING.getValue());

//        if(drone.getPackages()!=null){
//            pack=drone.getPackages();
//        }

        ////////////////////////////////

        Integer index = getLoadingPackageIndex(drone.getPackages());

        if(index!=null){
            pack = drone.getPackages().get(index);
        }else {
            pack = new Package();
        }

        String status = drone.getState();
        if(status.equals(DroneStatus.LOADED)){
            pack.setPackageStatus(PackageStatus.LOADED.getValue());
        }else if(status.equals(DroneStatus.LOADING)){
            pack.setPackageStatus(PackageStatus.LOADING.getValue());
        }else if(status.equals(DroneStatus.IDLE)){
            pack.setPackageStatus(null);
        }else if(status.equals(DroneStatus.DELIVERING)){
            pack.setPackageStatus(PackageStatus.DELIVERING.getValue());
        }else if(status.equals(DroneStatus.DELIVERED)){
            pack.setPackageStatus(PackageStatus.DELIVERED.getValue());
        }else{
            pack.setPackageStatus(PackageStatus.DELIVERED.getValue());
        }
        ////////////////////////

        pack.setDroneId(loadMedicationsRequestDto.getDroneId());
        pack.setPackageStatus(PackageStatus.LOADING.getValue());
        pack = packageRepository.save(pack);



        Set<MedicationDto> medicationsDtoSet = loadMedicationsRequestDto.getMedications();

        if(medicationsDtoSet!= null){
            for (MedicationDto medicationDto : medicationsDtoSet) {
                Medication medication = new Medication();
                medication.setMedicationId(medicationDto.getId());
                medication.setMedicationName(medicationDto.getMedicationName());
                medication.setCode(medicationDto.getCode());
                medication.setWeight(medicationDto.getWeight());
                medication.setPackageId(pack.getPackageId());
                pack.getMedications().add(medication);
            }

        }

        pack.getMedications().forEach((medication -> {medicationValidator.validate(medication);}));

        packageValidator.validate(pack);
        this.getAndValidateModleMaxWeight(drone.getModelId(),pack.getMedications());
        pack = packageRepository.save(pack);
    }

    @SneakyThrows
    public void updateDroneStatus(Long droneId, String status){
        Package pack = null;
        Drone drone = droneRepository.findById(droneId).orElseThrow(() -> new Exception("Drone not found"));

        drone.setState(status);
        Integer index = getLoadingPackageIndex(drone.getPackages());

        if(index!=null){
            pack = drone.getPackages().get(index);
        }else {
            pack = new Package();
        }

        String packStatus = null;
        if(status.equals(DroneStatus.LOADED)){
            pack.setPackageStatus(PackageStatus.LOADED.getValue());
        }else if(status.equals(DroneStatus.LOADING)){
            pack.setPackageStatus(PackageStatus.LOADING.getValue());
        }else if(status.equals(DroneStatus.IDLE)){
            pack.setPackageStatus(null);
        }else if(status.equals(DroneStatus.DELIVERING)){
            pack.setPackageStatus(PackageStatus.DELIVERING.getValue());
        }else if(status.equals(DroneStatus.DELIVERED)){
            pack.setPackageStatus(PackageStatus.DELIVERED.getValue());
        }else{
            pack.setPackageStatus(PackageStatus.DELIVERED.getValue());
        }

        droneValidator.validate(drone);

        droneRepository.save(drone);
    }


    public Integer getLoadingPackageIndex(List<Package> packages){
        Integer index = null;
        if(packages!=null ){
            for(int i=0;i<packages.size();i++) {
                Package aPackage = packages.get(i);
                if (aPackage.getPackageStatus() != null && aPackage.getPackageStatus().equals(PackageStatus.LOADING.getValue())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
//
//    public Package getLoadingPackage(List<Package> packages){
//        Package pack=null;
//        if(packages!=null ){
//            for(Package aPackage : packages) {
//                if (aPackage.getPackageStatus() != null && aPackage.getPackageStatus().equals(PackageStatus.LOADING.getValue())) {
//                    pack = aPackage;
//                }
//            }
//        }
//        return pack;
//    }

}
