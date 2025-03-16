package org.javaguru.travel.insurance.core.validations;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CountryClassifierValidator implements ValidationOptional {

    private final ErrorValidationFactory errorsHandler;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        if (request.getCountry() != null && !request.getCountry().isBlank() &&
                classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", request.getCountry()).isEmpty()) {
            return Optional.of(errorsHandler.processing("ERROR_CODE_9"));
        }
        return Optional.empty();
    }
}