package org.javaguru.travel.insurance.core.underwriting.calculators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.core.underwriting.TravelPremium;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelMedicalCalculatePremiumService implements TravelPremium {

    private final DateTimeUtil dateTimeUtil;
    private final CountryDefaultDayRateRepository countryDefaultDayRate;
    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        return calculateDayCount(request)
                .multiply(findCountryDefaultDayRate(request));
    }

    private BigDecimal calculateDayCount(TravelCalculatePremiumRequest request) {
        return new BigDecimal(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(),request.getAgreementDateTo()));
    }
    private BigDecimal findCountryDefaultDayRate(TravelCalculatePremiumRequest request){
        return countryDefaultDayRate.findDefaultDayRateByCountryIc(request.getCountry())
                .orElseThrow(() ->
                        new RuntimeException("Country not found = " + request.getCountry()));
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
