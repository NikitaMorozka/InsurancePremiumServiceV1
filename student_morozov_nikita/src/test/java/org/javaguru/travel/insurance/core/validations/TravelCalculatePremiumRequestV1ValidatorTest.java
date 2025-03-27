package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
class TravelCalculatePremiumRequestV1ValidatorTest {

    @Mock
    TravelCalculatePremiumRequestV1 request;

    @InjectMocks
    private TravelCalculatePremiumRequestValidatorImpl requestValidator;

    @Test
    @DisplayName("Тест: 2 ошибки валидации")
    void shouldReturnErrorsForNullFields(){
        List<ValidationList> validationsList = Arrays.asList(mock(ValidationList.class), mock(ValidationList.class));
        List<ValidationOptional> validationsOptional = Arrays.asList(mock(ValidationOptional.class), mock(ValidationOptional.class));

        when(validationsList.getFirst().validationList(request)).thenReturn(List.of(new ValidationError()));
        when(validationsList.getLast().validationList(request)).thenReturn(List.of(new ValidationError()));

        when(validationsOptional.getFirst().validationOptional(request)).thenReturn(Optional.of(new ValidationError()));
        when(validationsOptional.getLast().validationOptional(request)).thenReturn(Optional.of(new ValidationError()));

        ReflectionTestUtils.setField(requestValidator, "travelValidationLists", validationsList);
        ReflectionTestUtils.setField(requestValidator, "travelValidationOptionals", validationsOptional);

        List<ValidationError> errors = requestValidator.validate(request);

        assertEquals(4, errors.size());
    }

    @Test
    @DisplayName("Тест: Ошибок валидации нет")
    void shouldReturnErrorsForFields(){
        List<ValidationList> validationsList = Arrays.asList(mock(ValidationList.class));
        List<ValidationOptional> validationsOptional = Arrays.asList(mock(ValidationOptional.class));

        when(validationsList.getFirst().validationList(request)).thenReturn(List.of());
        when(validationsOptional.getLast().validationOptional(request)).thenReturn(Optional.empty());

        ReflectionTestUtils.setField(requestValidator, "travelValidationLists", validationsList);
        ReflectionTestUtils.setField(requestValidator, "travelValidationOptionals", validationsOptional);

        List<ValidationError> errors = requestValidator.validate(request);

        assertTrue(errors.isEmpty());
    }

}
