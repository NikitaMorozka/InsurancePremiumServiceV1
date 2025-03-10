package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.List;
import java.util.Optional;

//переделать типизированный интерфейс
interface Validation {
    Optional<ValidationError> validation(TravelCalculatePremiumRequest request);
    List<ValidationError> validationList(TravelCalculatePremiumRequest request);
}
