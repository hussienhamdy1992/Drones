package com.musala.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class JavaxValidator<T> {

    @Autowired
    private Validator validator;


    public boolean validate(T object){
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        boolean isFirstViolation=true;
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {

                String rootBeanClass = constraintViolation.getRootBeanClass().getName();
                String propertyPath = constraintViolation.getPropertyPath().toString();
                String validationName  = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                String resource = rootBeanClass+"."+propertyPath+"."+validationName;
                System.out.println();

                if(isFirstViolation){
                    sb.append(resource);
                    isFirstViolation=false;
                }else{
                    sb.append(","+resource);
                }

            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
        return true;
    }


}
