package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)

class CountryNotNullValidator implements ValidationOptional{
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        return checkingRequest(request)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_9"))
                : Optional.empty();
    }

    private boolean checkingRequest(TravelCalculatePremiumRequest checkingValue){
        return checkingValue.getCountry() == null
                || checkingValue.getCountry().isBlank()
                || !checkingValue.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }


}
