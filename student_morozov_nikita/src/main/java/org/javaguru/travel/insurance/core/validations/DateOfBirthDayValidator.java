package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class DateOfBirthDayValidator implements ValidationOptional {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        if (request.getDateOfBirth() == null) return Optional.of(errorsHandler.processing("ERROR_CODE_11"));
        return request.getDateOfBirth().isAfter(LocalDate.now())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_12"))
                : Optional.empty();
    }
}

