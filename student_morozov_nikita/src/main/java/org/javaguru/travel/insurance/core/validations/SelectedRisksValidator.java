package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)

public class SelectedRisksValidator implements ListValidation {

    private final ClassifierValueRepository classifierValueRepository;
    private final ErrorValidationFactory errorsHandler;

    @Override
    public List<ValidationError> validationList(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks() != null
                ? validateSelectedRisks(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedRisks(TravelCalculatePremiumRequest request) {
        return request.getSelectedRisks().stream()
                .map(this::validateRiskIc)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<ValidationError> validateRiskIc(String riskIc) {
        return !existInDatabase(riskIc)
                ? Optional.of(buildValidationError(riskIc))
                : Optional.empty();
    }

    private ValidationError buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_RISK_TYPE", riskIc);
        return errorsHandler.processing("ERROR_CODE_8", List.of(placeholder));
    }

    private boolean existInDatabase(String riskIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", riskIc).isPresent();
    }

}
