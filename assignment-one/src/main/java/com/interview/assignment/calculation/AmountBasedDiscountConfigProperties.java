package com.interview.assignment.calculation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;

@ConfigurationProperties(ignoreUnknownFields = true, prefix = "assignment.discounts.amount")
@Validated
class AmountBasedDiscountConfigProperties {

    @NotNull(message = "-------------- Thresholds need to be configured for amount based discount! --------------")
    @NotEmpty(message = "-------------- Thresholds need to be configured for amount based discount and cant be empty! --------------")
    private Map<BigDecimal, BigDecimal> thresholds;

    public Map<BigDecimal, BigDecimal> getThresholds() {
        return thresholds;
    }

    public void setThresholds(Map<BigDecimal, BigDecimal> thresholds) {
        this.thresholds = thresholds;
    }
}
