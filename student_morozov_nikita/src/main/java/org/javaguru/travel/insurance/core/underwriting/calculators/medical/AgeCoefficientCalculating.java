package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AgeCoefficientCalculating {

    @Value("${medical.risk.age.coefficient.enabled}")
    private boolean medicalRiskAgeCoefficientEnabled;

    private final AgeCoefficientRepository ageCoefficient;

    BigDecimal findAgeCoefficient(TravelCalculatePremiumRequest request) {
        return medicalRiskAgeCoefficientEnabled
                ? getCoefficient(request)
                : getDefaultValue();
    }

    private BigDecimal getCoefficient(TravelCalculatePremiumRequest request) {
        int age = (int) ChronoUnit.YEARS.between(request.getDateOfBirth(), LocalDate.now());
        return ageCoefficient.findByAgeCoefficient(age)
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(() ->
                        new RuntimeException("Coefficient not found = " + age));
    }


    private static BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }

}
