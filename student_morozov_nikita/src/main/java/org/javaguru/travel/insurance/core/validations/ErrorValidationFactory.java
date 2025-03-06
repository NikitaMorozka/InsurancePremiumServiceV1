package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.ErrorMessageUtil;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Component

public class ErrorValidationFactory {

    private final ErrorMessageUtil errorsConf;

    public ValidationError processing(String error) {
        String err = errorsConf.getProps(error);
        return new ValidationError(error, err);
    }
} 
