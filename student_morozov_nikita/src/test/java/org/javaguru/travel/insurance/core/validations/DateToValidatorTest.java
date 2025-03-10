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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateToValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateToValidator dateToValidator;

    @Test
    @DisplayName("Тест: правильно заполненные поля")
    void shouldNotReturnErrorForValidDateFrom() {
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025, 1, 1));

        Optional<ValidationError> validateErrors = dateToValidator.validation(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: поле DateTo не должно быть null")
    void shouldReturnErrorWhenDateToIsNull() {
        when(request.getAgreementDateTo()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_4")).thenReturn(new ValidationError("ERROR_CODE_4","DateTo must not be null!"));

        Optional<ValidationError> validationError = dateToValidator.validation(request);

        assertTrue(validationError.isPresent());
        assertEquals(validationError.get().getErrorCode(), "ERROR_CODE_4");
        assertEquals(validationError.get().getDescription(), "DateTo must not be null!");
    }

}
