package org.javaguru.travel.insurance.rest;


import com.google.common.base.Stopwatch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.rest.logger.EventLogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//testtest
@RestController
@RequestMapping("/insurance/travel/api")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelCalculatePremiumController {

    private final TravelCalculatePremiumService calculatePremiumService;
    private final List<EventLogger> loggers;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")

    public TravelCalculatePremiumResponseV1 calculatePremium(@RequestBody TravelCalculatePremiumRequestV1 request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logObject(request);
        TravelCalculatePremiumResponseV1 response = calculatePremiumService.calculatePremium(request);
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