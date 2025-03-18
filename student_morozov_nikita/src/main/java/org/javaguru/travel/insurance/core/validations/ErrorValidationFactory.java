package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.util.ErrorMessageUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class ErrorValidationFactory {

    private final ErrorMessageUtil errorCodeUtil;

    ValidationError processing(String error) {
        String err = errorCodeUtil.getErrorDescription(error);
        return new ValidationError(error, err);
    }

    ValidationError processing(String errorCode, List<Placeholder> placeholders) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);
        return new ValidationError(errorCode, errorDescription);
    }
} 
