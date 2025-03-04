package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatePremiumUnderwritingTest {

    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks
    CalculatePremiumUnderwriting calculatePremiumUnderwriting;

    @Test
    @DisplayName("Тест: проверка на вывод положительного числа")
    void checkingPositiveNumber(){
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2025,2,25));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025,4,25));
        BigDecimal value = calculatePremiumUnderwriting.calculateUnderwriting(request);
        assertNotNull(value);
    }

    @Test
    @DisplayName("Тест: проверка на вывод нуля")
    void checkingNullNumber(){
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2025,4,25));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025,4,25));
        BigDecimal value = calculatePremiumUnderwriting.calculateUnderwriting(request);
        assertEquals(value, BigDecimal.valueOf(0L));
    }

    @Test
    @DisplayName("Тест: проверка на вывод отрицательного числа")
    void checkingNegativeNumber(){
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.of(2025,4,25));
        when(request.getAgreementDateTo()).thenReturn(LocalDate.of(2025,2,25));
        BigDecimal value = calculatePremiumUnderwriting.calculateUnderwriting(request);
        assertEquals(value, BigDecimal.valueOf(-59L));
    }
}
