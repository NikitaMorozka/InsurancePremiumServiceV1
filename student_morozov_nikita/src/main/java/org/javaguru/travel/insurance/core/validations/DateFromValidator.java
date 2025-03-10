package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateFromValidator extends ValidationImpl {
//    private final ErrorsConf errorsConf;
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validation(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateFrom() == null)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_3"))
                : Optional.empty();
    }
}
