package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryClassifierValidatorTest {
    @Mock ErrorValidationFactory errorsHandler;
    @Mock ClassifierValueRepository classifierValueRepository;
    @Mock TravelCalculatePremiumRequest request;

    @InjectMocks
    CountryClassifierValidator countryClassifierValidator;

    @Test
    void shouldReturnErrorWhenCountryNotInRepository() {
        when(request.getCountry()).thenReturn("JAPAN");
        when(errorsHandler.processing("ERROR_CODE_9")).thenReturn(new ValidationError("ERROR_CODE_9", "Country must not be null!"));
        Optional<ValidationError> result = countryClassifierValidator.validationOptional(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_9", result.get().errorCode());
    }
//    @Test
//    void shouldReturnErrorWhenCountryNotInRepository2() {
//        when(request.getCountry()).thenReturn("JAPAN");
//        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", request.getCountry())).thenReturn(Optional.of(new ClassifierValue("COUNTRY","ksdofosdf"));
//
//    }
}
