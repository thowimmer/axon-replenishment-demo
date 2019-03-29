package com.netcetera.demo.replenishment.domain.util;

public class ValidationUtil {

    public static void assertState(Enum actual, Enum expected){
        if(!actual.equals(expected)){
            throw new IllegalStateException(String.format("Operation not allowed. Aggregate is in state %s.", actual));
        }
    }
}
