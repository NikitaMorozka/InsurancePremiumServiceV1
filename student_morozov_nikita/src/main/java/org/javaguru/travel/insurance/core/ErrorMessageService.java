package org.javaguru.travel.insurance.core;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class ErrorMessageService {
    private Properties props;

    ErrorMessageService() {
        try {
            props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("errorCodes.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProps(String CODE_ERROR) {
        return props.getProperty(CODE_ERROR);
    }
}
