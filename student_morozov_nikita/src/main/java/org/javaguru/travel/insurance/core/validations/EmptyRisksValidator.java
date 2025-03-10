package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptyRisksValidator extends ValidationImpl{
    private final ErrorValidationFactory errorsHandler;


    //это переделать EmptyRisksValidatorTest
    @Override
    public Optional<ValidationError> validation(TravelCalculatePremiumRequest request) {
        List<String> risks = request.getSelectedRisks();

        boolean hasEmptyRisk = risks.stream().anyMatch(risk ->  risk == null || risk.trim().isEmpty());

        if (risks == null || risks.isEmpty() || hasEmptyRisk) {
            return Optional.of(errorsHandler.processing("ERROR_CODE_7"));
        }
        return Optional.empty();
    }
}
