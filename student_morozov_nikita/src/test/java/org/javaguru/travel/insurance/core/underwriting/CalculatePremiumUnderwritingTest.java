package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class CalculatePremiumUnderwritingTest {

    @Mock private SelectedRisksPremiumCalculator risks;
    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks
    private CalculatePremiumUnderwritingImpl calculatePremiumUnderwritingImpl;

    @Test
    void shouldCalculatePremiumForOneRisk() {

        List<Risks> risksList = List.of(
                new Risks("TRAVEL_MEDICAL", new BigDecimal(2L)),
                new Risks("TRAVEL_EVACUATION", new BigDecimal(2L))
        );

        when(risks.calculatePremiumForRisk(request)).thenReturn(risksList);
        TravelPremiumCalculationRisksResult result = calculatePremiumUnderwritingImpl.calculateUnderwriting(request);

        assertEquals(new BigDecimal(4L), result.totalPremium());
    }
}

