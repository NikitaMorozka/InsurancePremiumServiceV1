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
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class DateOfBirthAfterNowValidator implements ValidationOptional {

    private final ErrorValidationFactory errorsHandler;

    private LocalDate getCurrentDate() {
        ZoneId zoneId = ZoneId.of("UTC");
        return ZonedDateTime.now(zoneId).toLocalDate();
    }

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        LocalDate currentDate = getCurrentDate();
        if (request.getDateOfBirth() == null) return Optional.of(errorsHandler.processing("ERROR_CODE_11"));
        return request.getDateOfBirth().isAfter(currentDate)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_12"))
                : Optional.empty();
    }
}

