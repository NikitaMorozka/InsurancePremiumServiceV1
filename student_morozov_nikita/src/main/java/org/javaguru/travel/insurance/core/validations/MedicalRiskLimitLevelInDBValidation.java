package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MedicalRiskLimitLevelInDBValidation implements ValidationOptional {

    private final ClassifierValueRepository classifierValueRepository;
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequestV1 request) {
        return (isMedicalRiskLimitLevelNotBlank(request))
                || !existInDatabase(request.getMedicalRiskLimitLevel())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelNotBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getMedicalRiskLimitLevel() == null
                || request.getMedicalRiskLimitLevel().isBlank();
    }

    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
    }
}

