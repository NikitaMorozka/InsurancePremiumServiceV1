package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.underwriting.TravelPremium;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TravelSportActivitiesCalculatePremiumService implements TravelPremium {

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequestV1 request) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_SPORT_ACTIVITIES";
    }
}
