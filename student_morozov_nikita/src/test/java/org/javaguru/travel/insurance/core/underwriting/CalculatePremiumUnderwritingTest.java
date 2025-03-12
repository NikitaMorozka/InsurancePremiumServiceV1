package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatePremiumUnderwritingTest {

    @Mock private TravelPremium travelPremium1;
    @Mock private TravelPremium travelPremium2;
    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks
    private CalculatePremiumUnderwritingImpl calculatePremiumUnderwritingImpl;

    @BeforeEach
    void setUp() {
        var riskPremiumCalculators = List.of(travelPremium1, travelPremium2);
        ReflectionTestUtils.setField(calculatePremiumUnderwritingImpl, "listRisks", riskPremiumCalculators); //кладем 2 риска
    }

    @Test
    void shouldCalculatePremiumForOneRisk() {
        when(travelPremium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(travelPremium1.calculatePremium(request)).thenReturn(BigDecimal.valueOf(2L));
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));

        BigDecimal premium = calculatePremiumUnderwritingImpl.calculateUnderwriting(request).getPremium();

        assertEquals(BigDecimal.valueOf(2L), premium);
    }

    @Test
    void shouldCalculatePremiumForTwoRisk() {
        when(travelPremium1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(travelPremium2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");
        when(travelPremium1.calculatePremium(request)).thenReturn(BigDecimal.valueOf(2L));
        when(travelPremium2.calculatePremium(request)).thenReturn(BigDecimal.valueOf(2L));
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_EVACUATION"));

        BigDecimal premium = calculatePremiumUnderwritingImpl.calculateUnderwriting(request).getPremium();

        assertEquals(BigDecimal.valueOf(4L), premium);
    }


}

