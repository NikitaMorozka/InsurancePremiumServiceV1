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
class CountryInDataBaseValidator implements ValidationOptional {
    private final ErrorValidationFactory errorsHandler;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        if (containsTravelMedical(request) && countryIsNullOrBlank(request)) {
            return Optional.of(errorsHandler.processing("ERROR_CODE_10"));
        }
        return Optional.empty();
    }

    private boolean containsTravelMedical(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private boolean countryIsNullOrBlank(TravelCalculatePremiumRequest request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }

    private boolean checkingRequest(TravelCalculatePremiumRequest request) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", request.getCountry())
                .isEmpty();
    }

}
