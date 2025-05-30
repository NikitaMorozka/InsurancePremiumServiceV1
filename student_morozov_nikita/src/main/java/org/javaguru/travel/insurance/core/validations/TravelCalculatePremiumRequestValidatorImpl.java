package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumRequestValidatorImpl implements TravelCalculatePremiumRequestValidator {

    private final List<ValidationOptional> travelValidationOptionals;
    private final List<ValidationList> travelValidationLists;


    @Override
    public List<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> singleErrors = collectSingleErrors(request);
        List<ValidationError> listErrors = collectListErrors(request);
        return concatenateLists(singleErrors, listErrors);
    }

    private List<ValidationError> collectSingleErrors(TravelCalculatePremiumRequestV1 request) {
        return travelValidationOptionals.stream()
                .map(validation -> validation.validationOptional(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private List<ValidationError> collectListErrors(TravelCalculatePremiumRequestV1 request) {
        return travelValidationLists.stream()
                .map(validation -> validation.validationList(request))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<ValidationError> concatenateLists(List<ValidationError> singleErrors,
                                                   List<ValidationError> listErrors) {
        return Stream.concat(singleErrors.stream(), listErrors.stream())
                .toList();
    }



}
