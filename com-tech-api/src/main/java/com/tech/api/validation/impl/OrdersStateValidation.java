package com.tech.api.validation.impl;

import com.tech.api.constant.Constants;
import com.tech.api.validation.OrdersState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrdersStateValidation implements ConstraintValidator<OrdersState, Integer> {
    private boolean allowNull;
    @Override
    public void initialize(OrdersState constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer ordersState, ConstraintValidatorContext constraintValidatorContext) {
        if(ordersState == null && allowNull){
            return true;
        }
        if(!ordersState.equals(Constants.ORDERS_STATE_CREATED)
            &&!ordersState.equals(Constants.ORDERS_STATE_ACCEPTED)
            &&!ordersState.equals(Constants.ORDERS_STATE_COMPLETED)
            &&!ordersState.equals(Constants.ORDERS_STATE_CANCELED)
            &&!ordersState.equals(Constants.ORDERS_STATE_SHIPPING)){
                return false;
        }
        return true;
    }
}
