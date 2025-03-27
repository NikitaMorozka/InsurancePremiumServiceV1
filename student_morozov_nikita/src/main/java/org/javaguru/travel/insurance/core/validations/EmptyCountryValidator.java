package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyCountryValidator implements ValidationOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequestV1 request) {
        return (containsTravel(request) || countryIsNullOrBlank(request))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean containsTravel(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks() == null;
    }

    private boolean countryIsNullOrBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }

}
