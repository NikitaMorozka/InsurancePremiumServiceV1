package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    CountryDefaultDayRateCalculating countryDefaultDayRateCalculating;
    @Mock
    DayCountCalculating dayCountCalculating;
    @Mock
    AgeCoefficientCalculating ageCoefficientCalculating;
    @Mock
    MedicalRiskLimitLevelCalculating medicalRiskLimitLevelCalculating;

    @Mock
    TravelCalculatePremiumRequestV1 request;


    @InjectMocks
    TravelMedicalCalculatePremiumService calculator;

    @Test
    void shouldCalculatePremium() {
        when(dayCountCalculating.calculateDayCount(request)).thenReturn(new BigDecimal(2));
        when(countryDefaultDayRateCalculating.findCountryDefaultDayRate(request)).thenReturn(BigDecimal.TEN);
        when(ageCoefficientCalculating.findAgeCoefficient(request)).thenReturn(new BigDecimal(1.2));
        when(medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(request)).thenReturn(new BigDecimal(1.2));
        BigDecimal premium = calculator.calculatePremium(request);
        assertEquals(new BigDecimal("28.80"), premium);
    }

}