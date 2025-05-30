package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptyRisksValidator implements ValidationOptional {
    private final ErrorValidationFactory errorsHandler;


    @Override
    public Optional<ValidationError> validationOptional(TravelCalculatePremiumRequestV1 request) {
        List<String> risks = request.getSelectedRisks();

        boolean hasEmptyRisk = risks.stream().anyMatch(risk -> risk == null || risk.trim().isEmpty());

        return (risks.isEmpty() || hasEmptyRisk)
                ? Optional.of(errorsHandler.processing("ERROR_CODE_7"))
                : Optional.empty();
    }
}

