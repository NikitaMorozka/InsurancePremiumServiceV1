package org.javaguru.travel.insurance.core;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
class DateTimeService {
    Long calculateDaysDifference(LocalDate dateFrom, LocalDate dateTo ){
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }
}
