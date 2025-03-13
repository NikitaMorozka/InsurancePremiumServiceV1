package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CountInDataBaseValidator implements ValidationOptional {
    private final ErrorValidationFactory errorsHandler;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        return (checkingRequest(request) && request.getSelectedRisks() != null)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean checkingRequest(TravelCalculatePremiumRequest checkingValue) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", checkingValue.getCountry())
                .isEmpty();
    }

}
