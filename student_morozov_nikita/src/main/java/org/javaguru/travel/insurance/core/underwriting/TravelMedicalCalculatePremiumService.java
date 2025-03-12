package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelMedicalCalculatePremiumService implements TravelPremium{

    private final DateTimeUtil dateTimeUtil;

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(),request.getAgreementDateTo()));
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
