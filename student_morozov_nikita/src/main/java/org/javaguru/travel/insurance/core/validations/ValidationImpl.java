package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.List;
import java.util.Optional;

abstract class ValidationImpl implements Validation {
    @Override
    public Optional<ValidationError> validation(TravelCalculatePremiumRequest request) {
        return Optional.empty();
    }

    @Override
    public List<ValidationError> validationList(TravelCalculatePremiumRequest request) {
        return List.of();
    }
}
