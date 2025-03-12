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
        return check(request.getAgreementDateFrom(),request.getAgreementDateTo())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_5"))
                : Optional.empty();

    }

    private boolean check(LocalDate dateFrom, LocalDate dateTo) {
        return (dateFrom != null && dateTo != null)
                && (dateTo.equals(dateFrom) || dateFrom.isAfter(dateTo));
    }
}
