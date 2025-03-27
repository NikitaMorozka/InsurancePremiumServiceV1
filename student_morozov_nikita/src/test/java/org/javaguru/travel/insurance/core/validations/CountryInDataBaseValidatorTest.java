package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
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
class CountryInDataBaseValidatorTest {

    @Mock CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Mock ErrorValidationFactory errorsHandler;
    @Mock
    TravelCalculatePremiumRequestV1 request;

    @InjectMocks
    CountryInDataBaseValidator countryInDataBaseValidator;

    @Test
    @DisplayName("Тест: Страна нет в БД")
    void shouldReturnErrorWhenCountryNotInRepository() {
        when(request.getCountry()).thenReturn("NoNameCountry");
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("NoNameCountry"))
                .thenReturn(Optional.empty());
        when(errorsHandler.processing("ERROR_CODE_9"))
                .thenReturn(new ValidationError("ERROR_CODE_9", "Country not in DataBase"));

        Optional<ValidationError> result = countryInDataBaseValidator.validationOptional(request);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_9", result.get().errorCode());
        assertEquals("Country not in DataBase", result.get().description());
    }

    @Test
    @DisplayName("Тест: Страна есть в БД")
    void shouldNotReturnErrorWhenCountryFoundInRepository() {
        when(request.getCountry()).thenReturn("JAPAN");
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("JAPAN")).thenReturn(Optional.of(new CountryDefaultDayRate()));

        Optional<ValidationError> result = countryInDataBaseValidator.validationOptional(request);

        assertTrue(result.isEmpty());
    }

}
