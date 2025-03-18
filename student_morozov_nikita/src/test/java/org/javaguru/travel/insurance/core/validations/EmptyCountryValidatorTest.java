package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidatorTest {
    @Mock ErrorValidationFactory errorsHandler;
    @Mock TravelCalculatePremiumRequest request;

    @InjectMocks private EmptyCountryValidator validation;

    @Test
    void shouldReturnNoErrorWhenSelectedRisksIsNull() {
        when(request.getSelectedRisks()).thenReturn(null);

        Optional<ValidationError> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isEmpty());
    }

    @Test
    void shouldReturnNoErrorWhenSelectedRisksNotContainsTravelMedical() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_EVACUATION"));

        Optional<ValidationError> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isEmpty());
    }

    @Test
    void shouldReturnNoErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsPresent() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn("SPAIN");

        Optional<ValidationError> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsNull() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_10")).thenReturn(new ValidationError("ERROR_CODE_10", "Country not in DataBase"));

        Optional<ValidationError> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_10", errorOpt.get().errorCode());
        assertEquals("Country not in DataBase", errorOpt.get().description());
    }

    @Test
    void shouldReturnErrorWhenSelectedRisksContainsTravelMedicalAndCountryIsEmpty() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getCountry()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_10"))
                .thenReturn(new ValidationError("ERROR_CODE_10", "Country not in DataBase"));

        Optional<ValidationError> errorOpt = validation.validationOptional(request);

        assertTrue(errorOpt.isPresent());
        assertEquals("ERROR_CODE_10", errorOpt.get().errorCode());
        assertEquals("Country not in DataBase", errorOpt.get().description());
    }
}
