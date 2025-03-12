package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateAfterNowValidator implements OptionalValidation {
    private final ErrorValidationFactory errorsHandler;

    private LocalDate getCurrentDate() {
        ZoneId zoneId = ZoneId.of("UTC");
        return ZonedDateTime.now(zoneId).toLocalDate();
    }

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        return check(request.getAgreementDateFrom(), request.getAgreementDateTo())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_6"))
                : Optional.empty();
    }

    private boolean check(LocalDate dateFrom, LocalDate dateTo) {
        LocalDate currentDate = getCurrentDate();
        return (dateFrom != null && dateTo != null)
                && (dateTo.isBefore(currentDate) || dateFrom.isBefore(currentDate));
    }
}
