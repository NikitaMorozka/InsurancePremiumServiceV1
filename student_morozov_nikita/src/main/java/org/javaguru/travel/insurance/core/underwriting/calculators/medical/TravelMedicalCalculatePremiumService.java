package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.TravelPremium;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelMedicalCalculatePremiumService implements TravelPremium {
    private final DayCountCalculating dayCountCalculating;
    private final CountryDefaultDayRateCalculating dayRate;
    private final AgeCoefficientCalculating ageCoefficientCalculating;
    private final MedicalRiskLimitLevelCalculating medicalRiskLimitLevelCalculating;

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequestV1 request) {
        return dayCountCalculating.calculateDayCount(request)
                .multiply(dayRate.findCountryDefaultDayRate(request))
                .multiply(ageCoefficientCalculating.findAgeCoefficient(request))
                .multiply(medicalRiskLimitLevelCalculating.findMedicalRiskLimitLevel(request))
                .setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
