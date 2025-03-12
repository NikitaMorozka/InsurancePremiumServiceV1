package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DateAfterNowValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;//подменяемый объект
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateAfterNowValidator dateAfterNowValidator;

    @Test
    @DisplayName("Тест: дата начала соглашения должна быть в будущем")
    void shouldNotReturnErrorForValidDateRange() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(request.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));

        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.validation(request);

        assertTrue(validateDateAfterNow.isEmpty());
    }

    @Test
    @DisplayName("Тест: дата начала соглашения не может быть в прошлом")
    void shouldReturnErrorForPastAgreementDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate().minusDays(1));
        when(request.getAgreementDateTo()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate());
        when(errorsHandler.processing("ERROR_CODE_6")).thenReturn(new ValidationError("ERROR_CODE_6","Both DateFrom and DateTo must be in the future!"));

        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.validation(request);

        assertTrue(validateDateAfterNow.isPresent());
        assertEquals("ERROR_CODE_6", validateDateAfterNow.get().getErrorCode());
        assertEquals("Both DateFrom and DateTo must be in the future!", validateDateAfterNow.get().getDescription());

    }

    @Test
    @DisplayName("Тест: обе даты должны быть в будущем")
    void shouldReturnErrorForNonFutureAgreementDates() {
        when(request.getAgreementDateFrom()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate().minusDays(1));
        when(request.getAgreementDateTo()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate().plusDays(1));
        when(errorsHandler.processing("ERROR_CODE_6")).thenReturn(new ValidationError("ERROR_CODE_6","Both DateFrom and DateTo must be in the future!"));

        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.validation(request);

        assertTrue(validateDateAfterNow.isPresent());
        assertEquals("ERROR_CODE_6", validateDateAfterNow.get().getErrorCode());
        assertEquals("Both DateFrom and DateTo must be in the future!", validateDateAfterNow.get().getDescription());
    }

}
