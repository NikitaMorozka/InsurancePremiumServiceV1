package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Подключаем расширение Mockito
public class TravelCalculatePremiumRequestValidatorTest {

    @Mock
    TravelCalculatePremiumRequest request;

    @InjectMocks
    private TravelCalculatePremiumRequestImpl requestValidator;


    @Test
    @DisplayName("Тест: 2 ошибки валидации")
    public void shouldReturnErrorsForNullFields(){
        List<Validation> validations = Arrays.asList(mock(Validation.class), mock(Validation.class));
        when(validations.getFirst().executeValidation(request)).thenReturn(Optional.of(new ValidationError()));
        when(validations.getLast().executeValidation(request)).thenReturn(Optional.of(new ValidationError()));

        ReflectionTestUtils.setField(requestValidator, "travelValidations", validations);
        List<ValidationError> errors = requestValidator.validate(request);
        assertEquals(errors.size(), 2);
    }

    @Test
    @DisplayName("Тест: Ошибок валидации нет")
    public void shouldReturnErrorsForFields(){
        List<Validation> validations = Arrays.asList(mock(Validation.class), mock(Validation.class));
        when(validations.getFirst().executeValidation(request)).thenReturn(Optional.empty());
        when(validations.getLast().executeValidation(request)).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(requestValidator, "travelValidations", validations);
        List<ValidationError> errors = requestValidator.validate(request);
        assertTrue(errors.isEmpty());
    }

}
