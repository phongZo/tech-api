package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.PaymentMethod;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentMethodValidation implements ConstraintValidator<PaymentMethod, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(PaymentMethod constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer paymentMethod, ConstraintValidatorContext constraintValidatorContext) {
        if(paymentMethod == null && allowNull){
            return true;
        }
        if(!paymentMethod.equals(Constants.PAYMENT_METHOD_COD)
            &&!paymentMethod.equals(Constants.PAYMENT_METHOD_ONLINE)){
            return false;
        }
        return true;
    }
}
