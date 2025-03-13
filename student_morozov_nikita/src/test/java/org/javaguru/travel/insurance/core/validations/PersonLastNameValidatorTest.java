package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)


class PersonLastNameValidatorTest {

    @Mock private TravelCalculatePremiumRequest request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    PersonLastNameValidator personLastNameValidator;

    @Test
    @DisplayName("Тест: поле должно быть заполнено")
    void shouldNotReturnErrorWhenPersonLastNameIsProvided() {
        when(request.getPersonLastName()).thenReturn("Name");

        Optional<ValidationError> validateErrors = personLastNameValidator.validationOptional(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (null)")
    void shouldReturnErrorWhenPersonLastNameIsNull() {
        when(request.getPersonLastName()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_2")).thenReturn(new ValidationError("ERROR_CODE_2","Field personLastName is empty!"));

        Optional<ValidationError> validateErrors = personLastNameValidator.validationOptional(request);

        assertEquals("ERROR_CODE_2", validateErrors.get().errorCode());
        assertEquals("Field personLastName is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (пустая строка)")
    void shouldReturnErrorWhenPersonLastNameIsEmpty() {
        when(request.getPersonLastName()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_2")).thenReturn(new ValidationError("ERROR_CODE_2","Field personLastName is empty!"));

        Optional<ValidationError> validateErrors = personLastNameValidator.validationOptional(request);

        assertEquals("ERROR_CODE_2", validateErrors.get().errorCode());
        assertEquals("Field personLastName is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

}
