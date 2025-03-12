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
    private final List<TravelPremium> listRisks;

    @Override
    public ListRisks calculateUnderwriting(TravelCalculatePremiumRequest request){
        List<Risks> finalListRisk = request.getSelectedRisks().stream().map(
                risk ->{
                    BigDecimal premium = calculatePremiumForRisk(risk, request);
                    return new Risks(risk,premium);
                }).toList();

       BigDecimal premium =  request.getSelectedRisks().stream()
               .map(risk -> calculatePremiumForRisk(risk, request))
               .reduce(BigDecimal.ZERO, BigDecimal::add);
       return new ListRisks(finalListRisk, premium);
    }

    private BigDecimal calculatePremiumForRisk(String risk,  TravelCalculatePremiumRequest request){
        var riskPremiumCalculator = getTravelPremium(risk);
        return riskPremiumCalculator.calculatePremium(request);
    }

    private TravelPremium getTravelPremium(String riskIc){
        return listRisks.stream()
                .filter(risk -> risk.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported"));
    }



}
