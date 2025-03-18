package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MedicalRiskLimitLevelValidation implements ValidationOptional{

    @Value("${medical.risk.limit.level.enabled}")
    private Boolean medicalRiskLimitLevelEnabled;
    private final ClassifierValueRepository classifierValueRepository;
    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
        return (medicalRiskLimitLevelEnabled
                && containsTravelMedical(request)
                && isMedicalRiskLimitLevelNotBlank(request))
                && !existInDatabase(request.getMedicalRiskLimitLevel())
                ? Optional.of(errorsHandler.processing("ERROR_CODE_13"))
                : Optional.empty();
    }

    private boolean containsTravelMedical(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }

    private boolean isMedicalRiskLimitLevelNotBlank(TravelCalculatePremiumRequest request) {
        return request.getMedicalRiskLimitLevel() != null && !request.getMedicalRiskLimitLevel().isBlank();
    }

    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
    }
}
