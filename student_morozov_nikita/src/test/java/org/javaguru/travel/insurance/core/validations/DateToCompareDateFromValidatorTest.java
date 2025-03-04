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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateToCompareDateFromValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateToCompareDateFromValidator dateToCompareDateFromValidator;

    @Test
    @DisplayName("Тест: дата начала должна быть раньше даты конца")
    void shouldNotReturnErrorWhenDateToIsNotLessThanDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2023, 5, 11));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2023, 6, 11));
        Optional<ValidationError> validationError = dateToCompareDateFromValidator.executeValidation(request);
        assertTrue(validationError.isEmpty());
    }

    @Test
    @DisplayName("Тест: дата окончания не должна быть меньше даты начала")
    void shouldReturnErrorWhenDateToIsLessThanDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2023, 6, 11));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2023, 5, 11));
        when(errorsHandler.processing("ERROR_CODE_5")).thenReturn(new ValidationError("ERROR_CODE_5", "DateTo must not be less DateFrom!"));
        Optional<ValidationError> validationError = dateToCompareDateFromValidator.executeValidation(request);
        assertTrue(validationError.isPresent());
        assertEquals(validationError.get().getErrorCode(), "ERROR_CODE_5");
        assertEquals(validationError.get().getDescription(), "DateTo must not be less DateFrom!");
    }

}
