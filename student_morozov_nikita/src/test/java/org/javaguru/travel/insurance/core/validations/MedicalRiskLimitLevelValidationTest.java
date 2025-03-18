package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelValidationTest {
    @Mock ClassifierValueRepository classifierValueRepository;
    @Mock ErrorValidationFactory errorValidationFactory;
    @Mock TravelCalculatePremiumRequest request;

    @InjectMocks MedicalRiskLimitLevelValidation medicalRiskLimitLevelValidation;
    @Test
    void shouldReturnError() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        when(classifierValueRepository.
                findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.empty());

        ValidationError validationError = mock(ValidationError.class);
        when(errorValidationFactory.processing("ERROR_CODE_13")).thenReturn(validationError);
        Optional<ValidationError> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);
        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationError, validationErrorOpt.get());
    }

}


// @Value("${medical.risk.limit.level.enabled}")
//    private Boolean medicalRiskLimitLevelEnabled;
//    private final ClassifierValueRepository classifierValueRepository;
//    private final ErrorValidationFactory errorsHandler;
//
//    @Override
//    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequest request) {
//        return (medicalRiskLimitLevelEnabled
//                && containsTravelMedical(request)
//                && isMedicalRiskLimitLevelNotBlank(request))
//                && !existInDatabase(request.getMedicalRiskLimitLevel())
//                ? Optional.of(errorsHandler.processing("ERROR_CODE_13"))
//                : Optional.empty();
//    }
//
//    private boolean containsTravelMedical(TravelCalculatePremiumRequest request) {
//        return request.getSelectedRisks() != null
//                && request.getSelectedRisks().contains("TRAVEL_MEDICAL");
//    }
//
//    private boolean isMedicalRiskLimitLevelNotBlank(TravelCalculatePremiumRequest request) {
//        return request.getMedicalRiskLimitLevel() != null && !request.getMedicalRiskLimitLevel().isBlank();
//    }
//
//    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
//        return classifierValueRepository
//                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
//    }