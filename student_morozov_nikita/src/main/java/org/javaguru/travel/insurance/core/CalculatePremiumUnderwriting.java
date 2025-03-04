package org.javaguru.travel.insurance.core;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CalculatePremiumUnderwriting {
    private final DateTimeService dateTimeService = new DateTimeService();

    BigDecimal calculateUnderwriting(TravelCalculatePremiumRequest travelCalculatePremiumRequest){
       return BigDecimal.valueOf(dateTimeService.calculateDaysDifference(
                       travelCalculatePremiumRequest.getAgreementDateFrom(),
                       travelCalculatePremiumRequest.getAgreementDateTo())
       );
    }


}
