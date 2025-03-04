package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.ErrorProcessorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SelectedRisksValidator implements Validation {
    private final ErrorProcessorFactory errorsHandler;

    @Override
    public Optional<ValidationError> executeValidation(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks().isEmpty()
                ? Optional.of(errorsHandler.processing("ERROR_CODE_7"))
                : Optional.empty();
    }
}
