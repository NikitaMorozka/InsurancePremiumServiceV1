package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.Risks;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksPremiumCalculatorTest {

    @Mock private TravelPremium premium1;
    @Mock private TravelPremium premium2;
    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @BeforeEach
    void setUp() {
        var riskPremiumCalculators = List.of(premium1, premium2);
        selectedRisksPremiumCalculator = new SelectedRisksPremiumCalculator(riskPremiumCalculators);
    }

    @Test
    void shouldCalculatePremiumForOneRisk() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium1.calculatePremium(request)).thenReturn(new BigDecimal(2L));
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));

        List<Risks> risksList = selectedRisksPremiumCalculator.calculatePremiumForRisk(request);

        assertEquals(new BigDecimal(2L), risksList.getFirst().getPremium());
        assertEquals("TRAVEL_MEDICAL",risksList.getFirst().getRiskIc());
        assertEquals(1, risksList.size());
    }

    @Test
    void shouldCalculatePremiumForTwoRisk() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium1.calculatePremium(request)).thenReturn(new BigDecimal(2L));
        when(premium2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");
        when(premium2.calculatePremium(request)).thenReturn(new BigDecimal(2L));
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_EVACUATION"));

        List<Risks> risksList = selectedRisksPremiumCalculator.calculatePremiumForRisk(request);

        assertEquals(new BigDecimal(2L), risksList.getLast().getPremium());
        assertEquals("TRAVEL_EVACUATION",risksList.getLast().getRiskIc());
        assertEquals(new BigDecimal(2L), risksList.getFirst().getPremium());
        assertEquals("TRAVEL_MEDICAL",risksList.getFirst().getRiskIc());
        assertEquals(2, risksList.size());
    }

    @Test
    void shouldThrowExceptionWhenSelectedRiskTypeNotSupported() {
        when(premium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(premium2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");
        when(request.getSelectedRisks()).thenReturn(List.of("NOT_SUPPORTED_RISK_TYPE"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> selectedRisksPremiumCalculator.calculatePremiumForRisk(request));

        assertEquals( "Not supported: NOT_SUPPORTED_RISK_TYPE",exception.getMessage());

    }

}
