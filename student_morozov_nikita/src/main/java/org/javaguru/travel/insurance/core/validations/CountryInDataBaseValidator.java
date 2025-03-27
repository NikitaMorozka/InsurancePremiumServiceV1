package org.javaguru.travel.insurance.core.validations;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CountryInDataBaseValidator implements ValidationOptional {

    private final ErrorValidationFactory errorsHandler;
    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequestV1 request) {
        return (request.getCountry() != null && !request.getCountry().isBlank() &&
                countryDefaultDayRateRepository.findDefaultDayRateByCountryIc(request.getCountry()).isEmpty())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_9"))
                : Optional.empty();
    }
}