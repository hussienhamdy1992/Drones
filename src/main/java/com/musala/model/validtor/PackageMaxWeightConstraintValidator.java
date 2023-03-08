package com.musala.model.validtor;


import com.musala.model.entities.Medication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class PackageMaxWeightConstraintValidator implements ConstraintValidator<PackageMaxWeight, Set<Medication>> {

    public boolean isValid(Set<Medication> s, ConstraintValidatorContext cvc) {
        if(s!=null){
            for(Medication m : s){
                System.out.println("MMMMMM====>"+m);
            }
        }
        boolean result=true;
        return result;
    }
}