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
class DateOfBirthAfterNowValidatorTest {

    @Mock private ErrorValidationFactory errorsHandler;
    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks DateOfBirthAfterNowValidator dateOfBirthAfterNowValidator;

    @Test
    @DisplayName("Тест: Дата рождения раньше настоящего")
    void test1(){
        when(request.getDateOfBirth()).thenReturn(LocalDate.of(2012, 11 , 25));

        Optional<ValidationError> validationError = dateOfBirthAfterNowValidator.validationOptional(request);

        assertTrue(validationError.isEmpty());
    }

    @Test
    @DisplayName("Тест: Дата рождения не должна быть null")
    void test2(){
        when(request.getDateOfBirth()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_11")).thenReturn(new ValidationError("ERROR_CODE_11", "Date of Birth must not be null"));

        Optional<ValidationError> validationError = dateOfBirthAfterNowValidator.validationOptional(request);

        assertEquals("ERROR_CODE_11", validationError.get().errorCode());
    }

    @Test
    @DisplayName("Тест: Дата рождения позже настоящего")
    void test3(){
        when(request.getDateOfBirth()).thenReturn(LocalDate.of(2028, 11 , 25));
        when(errorsHandler.processing("ERROR_CODE_12")).thenReturn(new ValidationError("ERROR_CODE_12", "Date of birth must not be in the future!"));

        Optional<ValidationError> validationError = dateOfBirthAfterNowValidator.validationOptional(request);

        assertEquals("ERROR_CODE_12", validationError.get().errorCode());
    }

}


