package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateAfterNowValidator implements Validation {
    private final ErrorValidationFactory errorsHandler;
    @Override

    // поумать как переделать
    public Optional<ValidationError> executeValidation(TravelCalculatePremiumRequest request) {
        LocalDate dateFrom = request.getAgreementDateFrom();
        LocalDate dateTo = request.getAgreementDateTo();
        if (dateFrom != null && dateTo != null) {
            if (dateTo.isBefore(LocalDate.now()) || dateFrom.isBefore(LocalDate.now())) {
                return Optional.of(errorsHandler.processing("ERROR_CODE_6"));
            }
        }
        return Optional.empty();
    }

}
