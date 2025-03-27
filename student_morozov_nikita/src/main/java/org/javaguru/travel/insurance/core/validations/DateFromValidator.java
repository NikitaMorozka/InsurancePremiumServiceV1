package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateFromValidator implements ValidationOptional {
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequestV1 request) {
        return (request.getAgreementDateFrom() == null)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_3"))
                : Optional.empty();
    }
}
