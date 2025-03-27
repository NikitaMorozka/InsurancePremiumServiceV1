package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelValidationTest {
    @Mock ClassifierValueRepository classifierValueRepository;
    @Mock ErrorValidationFactory errorValidationFactory;
    @Mock
    TravelCalculatePremiumRequestV1 request;

    @InjectMocks
    MedicalRiskLimitLevelValidation medicalRiskLimitLevelValidation;

    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelNotEnabled() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", false);

        Optional<ValidationError> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
    void shouldNotReturnErrorWhenNotContainTravelMedicalRisk() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);

        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_EVACUATION"));

        Optional<ValidationError> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn(null);

        Optional<ValidationError> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorValidationFactory);
    }
    @Test
     void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        ReflectionTestUtils.setField(medicalRiskLimitLevelValidation, "medicalRiskLimitLevelEnabled", true);
        ClassifierValue classifierValue = mock(ClassifierValue.class);
        when(classifierValueRepository.
                findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.of(classifierValue));

        Optional<ValidationError> validationErrorOpt = medicalRiskLimitLevelValidation.validationOptional(request);

        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(errorValidationFactory);
    }

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

