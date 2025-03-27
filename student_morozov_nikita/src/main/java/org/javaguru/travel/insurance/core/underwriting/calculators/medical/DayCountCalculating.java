package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DayCountCalculating {

    private final DateTimeUtil dateTimeUtil;

    BigDecimal calculateDayCount(TravelCalculatePremiumRequestV1 request) {
        return new BigDecimal(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo()));
    }
}
