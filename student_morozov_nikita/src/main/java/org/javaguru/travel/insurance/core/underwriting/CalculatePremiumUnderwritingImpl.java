package org.javaguru.travel.insurance.core.underwriting;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculatePremiumUnderwritingImpl implements CalculatePremiumUnderwriting {
    private final DateTimeUtil dateTimeUtil;

    @Override
    public BigDecimal calculateUnderwriting(TravelCalculatePremiumRequest travelCalculatePremiumRequest){
       return BigDecimal.valueOf(dateTimeUtil.calculateDaysDifference(
                       travelCalculatePremiumRequest.getAgreementDateFrom(),
                       travelCalculatePremiumRequest.getAgreementDateTo())
       );
    }


}
