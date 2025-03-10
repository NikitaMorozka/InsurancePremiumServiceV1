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
class DateToCompareDateFromValidator extends ValidationImpl {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validation(TravelCalculatePremiumRequest request) {
        LocalDate dateFrom = request.getAgreementDateFrom();
        LocalDate dateTo = request.getAgreementDateTo();
        if (dateFrom != null && dateTo != null) {
            if (dateTo.equals(dateFrom) || dateFrom.isAfter(dateTo)) {
                return Optional.of(errorsHandler.processing("ERROR_CODE_5"));
            }
        }
        return Optional.empty();
    }
}
