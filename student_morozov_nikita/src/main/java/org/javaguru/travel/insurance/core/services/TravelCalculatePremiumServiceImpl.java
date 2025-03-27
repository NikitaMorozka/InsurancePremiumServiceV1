package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.CalculatePremiumUnderwriting;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationRisksResult;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelCalculatePremiumRequestValidator requestValidator;
    private final CalculatePremiumUnderwriting calculatePremium;

    @Override
    public TravelCalculatePremiumResponseV1 calculatePremium(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> errors = requestValidator.validate(request);
        return errors.isEmpty()
                ? getTravelCalculatePremiumResponse(request, calculatePremium.calculateUnderwriting(request))
                : new TravelCalculatePremiumResponseV1(errors);

    }

    private TravelCalculatePremiumResponseV1 getTravelCalculatePremiumResponse(TravelCalculatePremiumRequestV1 request, TravelPremiumCalculationRisksResult calculateUnderwriting){
        return new TravelCalculatePremiumResponseV1(
                request.getPersonFirstName(),
                request.getPersonLastName(),
                request.getDateOfBirth(),
                request.getAgreementDateFrom(),
                request.getAgreementDateTo(),
                request.getCountry(),
                request.getMedicalRiskLimitLevel(),
                calculateUnderwriting.totalPremium(),
                calculateUnderwriting.riskPremiums()
        );
    }
}
