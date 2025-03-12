package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.CalculatePremiumUnderwriting;
import org.javaguru.travel.insurance.core.underwriting.ListRisks;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelCalculatePremiumRequestValidator requestValidator;
    private final CalculatePremiumUnderwriting calculatePremium;

    @Override
    public TravelCalculatePremiumResponse calculatePremium(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = requestValidator.validate(request);
        return errors.isEmpty()
                ? getTravelCalculatePremiumResponse(request, calculatePremium.calculateUnderwriting(request))
                : new TravelCalculatePremiumResponse(errors);

    }

    private TravelCalculatePremiumResponse getTravelCalculatePremiumResponse(TravelCalculatePremiumRequest request,  ListRisks calculateUnderwriting){
        return new TravelCalculatePremiumResponse(
                request.getPersonFirstName(),
                request.getPersonLastName(),
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                calculateUnderwriting.getPremium(),
                calculateUnderwriting.getRisks()
        );
    }
}
