package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
class DateToValidatorTest {

    @Mock private TravelCalculatePremiumRequestV1 request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    DateToValidator dateToValidator;

    @Test
    @DisplayName("Тест: правильно заполненные поля")
    void shouldNotReturnErrorForValidDateFrom() {
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025, 1, 1));

        Optional<ValidationError> validateErrors = dateToValidator.validationOptional(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: поле DateTo не должно быть null")
    void shouldReturnErrorWhenDateToIsNull() {
        when(request.getAgreementDateTo()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_4")).thenReturn(new ValidationError("ERROR_CODE_4","DateTo must not be null!"));

        Optional<ValidationError> validationError = dateToValidator.validationOptional(request);

        assertTrue(validationError.isPresent());
        assertEquals("ERROR_CODE_4", validationError.get().errorCode());
        assertEquals("DateTo must not be null!", validationError.get().description());
    }

}
