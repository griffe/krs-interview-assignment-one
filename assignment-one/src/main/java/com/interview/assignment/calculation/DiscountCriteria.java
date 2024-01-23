package com.interview.assignment.calculation;

public record DiscountCriteria(String discountPolicyType) {

    public final static String AMOUNT = "AMOUNT";
    public final static String PERCENTAGE = "PERCENTAGE";
    public final static String UNKNOWN = "UNKNOWN";


}
