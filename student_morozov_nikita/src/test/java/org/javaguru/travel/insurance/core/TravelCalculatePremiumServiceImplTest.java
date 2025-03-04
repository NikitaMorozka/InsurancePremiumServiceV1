package org.javaguru.travel.insurance.core;

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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class) // Подключаем расширение Mockito

class TravelCalculatePremiumServiceImplTest {

    @Mock private DateTimeService dateTimeService;// Создаём мок-зависимость
    @Mock private TravelCalculatePremiumRequestValidator requestValidator;// Создаём мок-зависимость
    @Mock private CalculatePremiumUnderwriting premiumUnderwriting;
    @Mock private TravelCalculatePremiumRequest request;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl calculate;// Внедряем моки в тестируемый класс


    @Test
    @DisplayName("Тест: совпадения поля PersonFirstName в запросе и ответе")
    public void shouldReturnResponseMatchingRequestFieldFirstName() {
        when(request.getPersonLastName()).thenReturn("PersonFirstName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        var response = calculate.calculatePremium(request);
        assertEquals(response.getPersonLastName(), "PersonFirstName");
    }

    @Test
    @DisplayName("Тест: совпадения поля PersonLastName в запросе и ответе")
    public void shouldReturnResponseMatchingRequestFieldLastName() throws ParseException {
        when(request.getPersonLastName()).thenReturn("PersonLastName");
        when(requestValidator.validate(request)).thenReturn(List.of());
        var response = calculate.calculatePremium(request);
        assertEquals(request.getPersonFirstName(),response.getPersonFirstName());
    }

    @Test
    @DisplayName("Тест: совпадения поля DateFrom в запросе и ответе")
    public void shouldReturnResponseMatchingRequestFieldDateFrom() throws ParseException {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestValidator.validate(request)).thenReturn(List.of());
        var response = calculate.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(),response.getAgreementDateFrom());
    }

    @Test
    @DisplayName("Тест: совпадения поля DateTo в запросе и ответе")
    public void shouldReturnResponseMatchingRequestFieldDateTo() throws ParseException {
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(requestValidator.validate(request)).thenReturn(List.of());
        var response = calculate.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(),response.getAgreementDateTo());
    }

    @Test
    @DisplayName("Тест: наличия AgreementPrice в ответе")
    public void shouldReturnDifferenceDays() throws ParseException {
        when(request.getPersonFirstName()).thenReturn("Name");
        when(request.getPersonLastName()).thenReturn("LastName");
        when(request.getAgreementDateFrom()).thenReturn(LocalDate.now());
        when(request.getAgreementDateTo()).thenReturn(LocalDate.now().plusDays(1));
       when(requestValidator.validate(request)).thenReturn(List.of());
        TravelCalculatePremiumResponse response = calculate.calculatePremium(request);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Тест: наличия ошибок в ответе при валидации запроса")
    public void shouldReturnResponseWithErrors() {
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    @DisplayName("Тест: количества ошибок в ответе")
    public void shouldReturnResponseWithCountErrors() {
        List<ValidationError> validationErrors = List.of(
                new ValidationError("field1", "message1"),
                new ValidationError("field2", "message2")
        );
        when(requestValidator.validate(request)).thenReturn(validationErrors);
        var response = calculate.calculatePremium(request);
        assertEquals(response.getErrors().size(),2);
    }

    @Test
    @DisplayName("Тест: на передачу полей из объекта validationErrors")
    public void shouldReturnResponseEqualsErrors() {
        var validationErrors = new ValidationError("field1", "message1");
        when(requestValidator.validate(request)).thenReturn(List.of(validationErrors));
        var response = calculate.calculatePremium(request);
        assertEquals(response.getErrors().getFirst().getErrorCode(),"field1");
        assertEquals(response.getErrors().getFirst().getDescription(),"message1");
        assertNull(response.getPersonFirstName());
    }

    @Test
    @DisplayName("Тест: пустых полей, и вызова конструктора с ошибками")
    public void allFieldsMustBeEmptyWhenResponseContainsError() {
        var request = new TravelCalculatePremiumRequest();
        var validationError = new ValidationError("field", "message");
        when(requestValidator.validate(request)).thenReturn(List.of(validationError));
        var response = calculate.calculatePremium(request);
        assertNull(response.getPersonFirstName());
        assertNull(response.getPersonLastName());
        assertNull(response.getAgreementDateFrom());
        assertNull(response.getAgreementDateTo());
        assertNull(response.getAgreementPrice());
        assertNotNull(response.getErrors().getFirst().getErrorCode());
        assertNotNull(response.getErrors().getFirst().getDescription());
    }

}