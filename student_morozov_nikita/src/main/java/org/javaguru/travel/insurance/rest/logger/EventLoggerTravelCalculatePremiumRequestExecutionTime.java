package org.javaguru.travel.insurance.rest.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.base.Stopwatch;

@Component
class EventLoggerTravelCalculatePremiumRequestExecutionTime implements EventLogger {
    private static final Logger logger = LoggerFactory.getLogger(EventLoggerTravelCalculatePremiumRequest.class);

    @Override
    public void getLog(Object obj) {
        if (obj instanceof Stopwatch stopwatch) {
            long time = stopwatch.stop().elapsed().toMillis();
            logger.info("Request processing time (ms): {}", time);
        }
    }
}
