package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.Optional;

interface ValidationOptional {
    Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request);
}
