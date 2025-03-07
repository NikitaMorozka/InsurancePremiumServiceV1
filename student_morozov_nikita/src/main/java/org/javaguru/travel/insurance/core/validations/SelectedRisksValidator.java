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

public class SelectedRisksValidator implements Validation {

    private final ErrorValidationFactory errorsHandler;

    @Override
    public Optional<ValidationError> executeValidation(TravelCalculatePremiumRequest request) {
        List<String> risks = request.getSelectedRisks();

        return (risks == null || risks.isEmpty() || risks.stream().allMatch(Objects::isNull))
                ? Optional.of(errorsHandler.processing("ERROR_CODE_7"))
                : Optional.empty();
    }
}
