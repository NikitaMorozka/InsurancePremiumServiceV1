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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateAfterNowValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;//подменяемый объект
    @Mock private ErrorProcessorFactory errorsHandler;

    @InjectMocks
    DateAfterNowValidator dateAfterNowValidator;

    @Test
    @DisplayName("Тест: дата начала соглашения должна быть в будущем")
    public void shouldNotReturnErrorForValidDateRange() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(request.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));
        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.executeValidation(request);
        assertTrue(validateDateAfterNow.isEmpty());
    }

    @Test
    @DisplayName("Тест: дата начала соглашения не может быть в прошлом")
    public void shouldReturnErrorForPastAgreementDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now().minusDays(1));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));
        when(errorsHandler.processing("ERROR_CODE_6")).thenReturn(new ValidationError("ERROR_CODE_6","Both DateFrom and DateTo must be in the future!"));
        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.executeValidation(request);
        assertTrue(validateDateAfterNow.isPresent());
        assertEquals(validateDateAfterNow.get().getErrorCode(), "ERROR_CODE_6");
        assertEquals(validateDateAfterNow.get().getDescription(), "Both DateFrom and DateTo must be in the future!");

    }

    @Test
    @DisplayName("Тест: обе даты должны быть в будущем")
    public void shouldReturnErrorForNonFutureAgreementDates() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2010, 1, 20));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2010, 1, 20));
        when(errorsHandler.processing("ERROR_CODE_6")).thenReturn(new ValidationError("ERROR_CODE_6","Both DateFrom and DateTo must be in the future!"));
        Optional<ValidationError> validateDateAfterNow = dateAfterNowValidator.executeValidation(request);
        assertTrue(validateDateAfterNow.isPresent());
        assertEquals(validateDateAfterNow.get().getErrorCode(), "ERROR_CODE_6");
        assertEquals(validateDateAfterNow.get().getDescription(), "Both DateFrom and DateTo must be in the future!");
    }

}
