package org.javaguru.travel.insurance.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class) // запуск спринг теста

public class TravelCalculatePremiumControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JsonFileReader jsonFileReader;

    public String getJsonRequest(String path) {
        return "src/test/resources/rest/request/" + path;
    }
    public String getJsonResponse(String path) {
        return "src/test/resources/rest/response/" + path;
    }

    public void ControllerTest(String jsonRequest, String jsonResponse) throws Exception {
        mockMvc.perform(post("/insurance/travel/")
                        .content(jsonFileReader.readJsonFromFile(jsonRequest))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFileReader.readJsonFromFile(jsonResponse)))
                .andReturn();
    }

    @Test
    public void simpleRestControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request1.JSON"),getJsonResponse("response1.JSON"));
    }
    @Test
    public void personFirstNameRestControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request2.JSON"),getJsonResponse("response2.JSON"));
    }
    @Test
    public void personLastNameRestControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request3.JSON"),getJsonResponse("response3.JSON"));
    }
    @Test
    public void agreementDateFromRestControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request4.JSON"),getJsonResponse("response4.JSON"));
    }
    @Test
    public void agreementDateToRestControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request5.JSON"),getJsonResponse("response5.JSON"));
    }
    @Test
    public void DateToLessDateFromControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request6.JSON"),getJsonResponse("response6.JSON"));
    }
    @Test
    public void PassValidationDateAfterNowControllerTest() throws Exception {
        ControllerTest(getJsonRequest("request7.JSON"),getJsonResponse("response7.JSON"));
    }

}
