package org.javaguru.travel.insurance.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class DateTimeServiceTest {

    @InjectMocks
    DateTimeUtil dateTimeUtil;

    @Test
    @DisplayName("Тест: проверка на вывод положительного числа")
    public void checkingPositiveNumber() {
        LocalDate testDateFromm = LocalDate.of(2025, 1, 11);
        LocalDate testDateToo = LocalDate.of(2025, 2, 11);

        long price = dateTimeUtil.calculateDaysDifference(testDateFromm, testDateToo);

        assertEquals(price, 31L);
    }

    @Test
    @DisplayName("Тест: проверка на вывод нуля")
    public void checkingNegativeNumber() {
        LocalDate testDateFromm = LocalDate.of(2025, 2, 11);
        LocalDate testDateToo = LocalDate.of(2025, 1, 11);

        long price = dateTimeUtil.calculateDaysDifference(testDateFromm, testDateToo);

        assertEquals(price, -31L);
    }

    @Test
    @DisplayName("Тест: проверка на вывод отрицательного числа")
    public void checkingZeroNumber() {
        LocalDate testDateFromm = LocalDate.of(2025, 1, 11);
        LocalDate testDateToo = LocalDate.of(2025, 1, 11);

        long price = dateTimeUtil.calculateDaysDifference(testDateFromm, testDateToo);

        assertEquals(price, 0);
    }
}
