package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
class PersonFirstNameValidatorTest {

    @Mock private TravelCalculatePremiumRequestV1 request;
    @Mock private ErrorValidationFactory errorsHandler;

    @InjectMocks
    PersonFirstNameValidator personFirstNameValidator;

    @Test
    @DisplayName("Тест: поле должно быть заполнено")
    void shouldNotReturnErrorWhenPersonFirstNameIsProvided() {
        when(request.getPersonFirstName()).thenReturn("Name");

        Optional<ValidationError> validateErrors = personFirstNameValidator.validationOptional(request);

        assertTrue(validateErrors.isEmpty());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (null)")
    void shouldReturnErrorWhenPersonFirstNameIsNull() {
        when(request.getPersonFirstName()).thenReturn(null);
        when(errorsHandler.processing("ERROR_CODE_1")).thenReturn(new ValidationError("ERROR_CODE_1","Field personFirstName is empty!"));

        Optional<ValidationError> validateErrors = personFirstNameValidator.validationOptional(request);

        assertEquals("ERROR_CODE_1", validateErrors.get().errorCode());
        assertEquals("Field personFirstName is empty!", validateErrors.get().description() );
        assertTrue(validateErrors.isPresent());
    }

    @Test
    @DisplayName("Тест: имя не должно быть пустым (пустая строка)")
    void shouldReturnErrorWhenPersonFirstNameIsEmpty() {
        when(request.getPersonFirstName()).thenReturn("");
        when(errorsHandler.processing("ERROR_CODE_1")).thenReturn(new ValidationError("ERROR_CODE_1","Field personFirstName is empty!"));

        Optional<ValidationError> validateErrors = personFirstNameValidator.validationOptional(request);

        assertEquals("ERROR_CODE_1", validateErrors.get().errorCode());
        assertEquals("Field personFirstName is empty!", validateErrors.get().description());
        assertTrue(validateErrors.isPresent());
    }

}
