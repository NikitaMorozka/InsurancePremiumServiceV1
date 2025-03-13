package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculatePremiumUnderwritingImpl implements CalculatePremiumUnderwriting {
    private final SelectedRisksPremiumCalculator risks;

    @Override
    public TravelPremiumCalculationRisksResult calculateUnderwriting(TravelCalculatePremiumRequest request){
       List<Risks> risksList = calculatePremiumForRisk(request);
       BigDecimal totalPremium = getTravelPremium(risksList);
       return new TravelPremiumCalculationRisksResult(risksList, totalPremium);
    }

    private List<Risks> calculatePremiumForRisk(TravelCalculatePremiumRequest request){
        return risks.calculatePremiumForRisk(request);
    }

    private BigDecimal getTravelPremium(List<Risks> listRisks){
        return listRisks.stream().map(Risks::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}
