package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.Risks;

import java.math.BigDecimal;
import java.util.List;

public record TravelPremiumCalculationRisksResult(List<Risks> riskPremiums, BigDecimal totalPremium) {
    public TravelPremiumCalculationRisksResult(List<Risks> riskPremiums, BigDecimal totalPremium){
        this.totalPremium = totalPremium;
        this.riskPremiums = List.copyOf(riskPremiums);
    }
}