package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.ErrorProcessorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateFromValidator implements Validation {
//    private final ErrorsConf errorsConf;
    private final ErrorProcessorFactory errorsHandler;

    @Override
    public Optional<ValidationError> executeValidation(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateFrom() == null)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_3"))
                : Optional.empty();
    }
}
