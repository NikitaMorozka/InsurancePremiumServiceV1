package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculatePremiumUnderwritingImpl implements CalculatePremiumUnderwriting {
    private final SelectedRisksPremiumCalculator risks;

    @Override
    public TravelPremiumCalculationRisksResult calculateUnderwriting(TravelCalculatePremiumRequestV1 request){
       List<Risks> risksList = calculatePremiumForRisk(request);
       BigDecimal totalPremium = getTravelPremium(risksList);
       return new TravelPremiumCalculationRisksResult(risksList, totalPremium);
    }

    private List<Risks> calculatePremiumForRisk(TravelCalculatePremiumRequestV1 request){
        return risks.calculatePremiumForRisk(request);
    }

    private BigDecimal getTravelPremium(List<Risks> listRisks){
        return listRisks.stream().map(Risks::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}
