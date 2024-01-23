package com.interview.assignment.calculation;

import java.util.List;

public class CalculationFailure {
    private final List<String> errors;

    public CalculationFailure(String error) {
        this.errors = List.of(error);
    }

    public CalculationFailure(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors.stream().toList();
    }

    void addError(String error) {
        errors.add(error);
    }
}
