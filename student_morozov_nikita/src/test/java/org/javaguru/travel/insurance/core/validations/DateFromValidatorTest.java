package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.ErrorProcessorFactory;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateFromValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;
    @Mock private ErrorProcessorFactory errorsHandler;

    @InjectMocks
    DateFromValidator dateFromValidator;

    @Test
    @DisplayName("Тест: правильно заполненные поля")
    void shouldNotReturnErrorForValidDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2025, 1, 1));

        Optional<ValidationError> validationError = dateFromValidator.executeValidation(request);
        assertFalse(validationError.isPresent());
    }

    @Test
    @DisplayName("Тест: поле DateFrom не должно быть null")
    void shouldReturnErrorWhenDateFromIsNull() {
        when(request.getAgreementDateFrom()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_3")).thenReturn(new ValidationError("ERROR_CODE_3","DateFrom must not be null!"));

        Optional<ValidationError> validationError = dateFromValidator.executeValidation(request);
        assertTrue(validationError.isPresent());
        assertEquals(validationError.get().getErrorCode(), "ERROR_CODE_3");
        assertEquals(validationError.get().getDescription(), "DateFrom must not be null!");
    }

}
