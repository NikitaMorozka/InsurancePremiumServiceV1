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

public class SelectedRisksPremiumCalculator  {

    private final List<TravelPremium> listRisks;

    public List<Risks> calculatePremiumForRisk(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks().stream()
                .map(risk -> new Risks(risk, calculatePremiumForRisk(risk, request)))
                .toList();
    }

    private BigDecimal calculatePremiumForRisk(String risk,  TravelCalculatePremiumRequestV1 request){
        return getTravelPremium(risk).calculatePremium(request);
    }

    private TravelPremium getTravelPremium(String riskIc){
        return listRisks.stream()
                .filter(risk -> risk.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported: " + riskIc));
    }
}
