package org.javaguru.travel.insurance.rest;


import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/insurance/travel")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

    private final TravelCalculatePremiumService calculatePremiumService;

    private final List<EventLogger> loggers;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")


    public TravelCalculatePremiumResponse calculatePremium(@RequestBody TravelCalculatePremiumRequest request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logObject(request);
        TravelCalculatePremiumResponse response = calculatePremiumService.calculatePremium(request);
        logObject(response);
        logObject(stopwatch);
        return response;
    }

    private void logObject(Object obj) {
        loggers.forEach(logger -> {
            try {
                logger.getLog(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}