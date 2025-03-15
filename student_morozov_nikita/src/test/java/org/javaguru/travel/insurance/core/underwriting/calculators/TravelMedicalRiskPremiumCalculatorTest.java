package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock DateTimeUtil dateTimeUtil;
    @Mock CountryDefaultDayRateRepository countryDefaultDayRateRepository;
    @Mock TravelCalculatePremiumRequest request;
    @Mock CountryDefaultDayRate countryDefaultDayRate;

    @InjectMocks TravelMedicalCalculatePremiumService calculator;

    @Test
    void shouldCalculatePremium() {
        when(request.getCountry()).thenReturn("SPAIN");
        when(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(2L);
        when(countryDefaultDayRate.getDefaultDayRate()).thenReturn(BigDecimal.TEN);
        when(countryDefaultDayRateRepository.findDefaultDayRateByCountryIc("SPAIN")).thenReturn(Optional.of(countryDefaultDayRate));
        BigDecimal premium = calculator.calculatePremium(request);
        assertEquals(new BigDecimal(20L),premium);
    }

}