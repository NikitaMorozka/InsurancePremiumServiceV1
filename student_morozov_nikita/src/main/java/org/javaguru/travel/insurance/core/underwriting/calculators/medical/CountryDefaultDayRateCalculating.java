package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CountryDefaultDayRateCalculating {

    private final CountryDefaultDayRateRepository countryDefaultDayRate;

    BigDecimal findCountryDefaultDayRate(TravelCalculatePremiumRequestV1 request) {
        return countryDefaultDayRate.findDefaultDayRateByCountryIc(request.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(() ->
                        new RuntimeException("Country not found = " + request.getCountry()));
    }
}
