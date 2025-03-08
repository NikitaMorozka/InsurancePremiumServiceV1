package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatePremiumUnderwritingTest {

    @Mock
    DateTimeUtil dateTimeUtil;
    @Mock
    private TravelCalculatePremiumRequest request;
    @InjectMocks
    CalculatePremiumUnderwritingImpl calculatePremiumUnderwritingImpl;

    @Test
    @DisplayName("Тест: проверка на вывод положительного числа")
    void checkingPositiveNumber() {
        when(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(2L);
        BigDecimal value = calculatePremiumUnderwritingImpl.calculateUnderwriting(request);

        assertEquals(value, new BigDecimal(2L));
    }

    @Test
    @DisplayName("Тест: проверка на вывод нуля")
    void checkingNullNumber() {
        when(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(0L);
        BigDecimal value = calculatePremiumUnderwritingImpl.calculateUnderwriting(request);

        assertEquals(value, new BigDecimal(0));
    }

    @Test
    @DisplayName("Тест: проверка на вывод отрицательного числа")
    void checkingNegativeNumber() {
        when(dateTimeUtil.calculateDaysDifference(request.getAgreementDateFrom(), request.getAgreementDateTo())).thenReturn(-2L);
        BigDecimal value = calculatePremiumUnderwritingImpl.calculateUnderwriting(request);

        assertEquals(value, BigDecimal.valueOf(-2L));
    }
}
