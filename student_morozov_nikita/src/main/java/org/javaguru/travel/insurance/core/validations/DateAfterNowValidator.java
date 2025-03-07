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
class DateAfterNowValidator implements Validation {
    private final ErrorValidationFactory errorsHandler;

    private LocalDate getCurrentDate(){
        ZoneId zoneId = ZoneId.of("UTC");
        return ZonedDateTime.now(zoneId).toLocalDate();
    }
    @Override
    public Optional<ValidationError> executeValidation(TravelCalculatePremiumRequest request) {
        LocalDate dateFrom = request.getAgreementDateFrom();
        LocalDate dateTo = request.getAgreementDateTo();
        LocalDate currentDate = getCurrentDate();

        if (dateFrom != null && dateTo != null) {
            if (dateTo.isBefore(currentDate) || dateFrom.isBefore(currentDate)) {
                return Optional.of(errorsHandler.processing("ERROR_CODE_6"));
            }
        }
        return Optional.empty();
    }

}
