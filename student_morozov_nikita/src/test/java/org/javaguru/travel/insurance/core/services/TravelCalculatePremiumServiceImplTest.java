package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.underwriting.CalculatePremiumUnderwriting;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationRisksResult;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Подключаем расширение Mockito
class TravelCalculatePremiumServiceImplTest {


    @Mock private CalculatePremiumUnderwriting calculatePremiumUnderwriting;
    @Mock  private TravelCalculatePremiumRequestValidator requestValidator;// Создаём мок-зависимость
    @Mock private TravelCalculatePremiumRequest request;
    @Mock private TravelPremiumCalculationRisksResult travelPremiumCalculationRisksResult;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl calculate;// Внедряем моки в тестируемый класс

    @Test
    @DisplayName("Тест: совпадения поля PersonFirstName в запросе и ответе")
    void shouldReturnResponseMatchingRequestFieldFirstName() {
        when(request.getPersonFirstName()).thenReturn("PersonFirstName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(calculatePremiumUnderwriting.calculateUnderwriting(request)).thenReturn(travelPremiumCalculationRisksResult);

        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);

        assertEquals("PersonFirstName", response.getPersonFirstName());
    }

    @Test
    @DisplayName("Тест: совпадения поля PersonLastName в запросе и ответе")
    void shouldReturnResponseMatchingRequestFieldLastName() {
        when(request.getPersonLastName()).thenReturn("PersonLastName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(calculatePremiumUnderwriting.calculateUnderwriting(request)).thenReturn(travelPremiumCalculationRisksResult);

        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);

        assertEquals(request.getPersonFirstName(), response.getPersonFirstName());
    }

    @Test
    @DisplayName("Тест: совпадения поля DateFrom в запросе и ответе")
    void shouldReturnResponseMatchingRequestFieldDateFrom() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(calculatePremiumUnderwriting.calculateUnderwriting(request)).thenReturn(travelPremiumCalculationRisksResult);

        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);

        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom());
    }

    @Test
    @DisplayName("Тест: совпадения поля DateTo в запросе и ответе")
    void shouldReturnResponseMatchingRequestFieldDateTo() {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(calculatePremiumUnderwriting.calculateUnderwriting(request)).thenReturn(travelPremiumCalculationRisksResult);

        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);

        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo());
    }

    @Test
    @DisplayName("Тест: наличия AgreementPremium в ответе")
    void shouldReturnDifferenceDays() {
        when(request.getAgreementDateFrom()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate());
        when(request.getAgreementDateTo()).thenReturn(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate().plusDays(1));
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(travelPremiumCalculationRisksResult.totalPremium()).thenReturn(new BigDecimal(10L));
        when(calculatePremiumUnderwriting.calculateUnderwriting(request)).thenReturn(travelPremiumCalculationRisksResult);

        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);

        assertEquals(new BigDecimal(10L), response.getAgreementPremium());
    }

    @Test
    @DisplayName("Тест: наличия ошибок в ответе при валидации запроса")
    void shouldReturnResponseWithErrors() {
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);

        assertTrue(response.hasErrors());
    }

    @Test
    @DisplayName("Тест: количества ошибок в ответе")
    void shouldReturnResponseWithCountErrors() {
        List<ValidationError> validationErrors = List.of(
                new ValidationError("field1", "message1"),
                new ValidationError("field2", "message2")
        );

        when(requestValidator.validate(request)).thenReturn(validationErrors);

        var response = calculate.calculatePremium(request);

        assertEquals(2, response.getErrors().size());
    }

    @Test
    @DisplayName("Тест: на передачу полей из объекта validationErrors")
    void shouldReturnResponseEqualsErrors() {
        var validationErrors = new ValidationError("field1", "message1");
        when(requestValidator.validate(request)).thenReturn(List.of(validationErrors));
        var response = calculate.calculatePremium(request);

        assertEquals( "field1", response.getErrors().getFirst().errorCode());
        assertEquals( "message1", response.getErrors().getFirst().description());
        assertNull(response.getPersonFirstName());
    }

    @Test
    @DisplayName("Тест: пустых полей, и вызова конструктора с ошибками")
    void allFieldsMustBeEmptyWhenResponseContainsError() {
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);

        assertNull(response.getPersonFirstName());
        assertNull(response.getPersonLastName());
        assertNull(response.getAgreementDateFrom());
        assertNull(response.getAgreementDateTo());
        assertNull(response.getAgreementPremium());
        assertNotNull(response.getErrors().getFirst().errorCode());
        assertNotNull(response.getErrors().getFirst().description());
    }

}