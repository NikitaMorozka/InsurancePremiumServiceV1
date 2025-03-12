package org.javaguru.travel.insurance.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class) // запуск спринг теста

class TravelCalculatePremiumControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonFileReader jsonFileReader;

    @ParameterizedTest
    @MethodSource({"jsonRequestResponseFiles"})
    void controllerTest(String jsonRequest, String jsonResponse) throws Exception {
        StringBuilder filesPathRequest = new StringBuilder("src/test/resources/rest/request/");
        StringBuilder filesPathResponse = new StringBuilder("src/test/resources/rest/response/");

        mockMvc.perform(post("/insurance/travel/")
                        .content(jsonFileReader.readJsonFromFile(filesPathRequest.append(jsonRequest).toString()))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFileReader.readJsonFromFile(filesPathResponse.append(jsonResponse).toString())))
                .andReturn();
    }

     static Stream<Arguments> jsonRequestResponseFiles() {

        return Stream.of(
                Arguments.of("request1.json", "response1.json"),
                Arguments.of("request2.json", "response2.json"),
                Arguments.of("request3.json", "response3.json"),
                Arguments.of("request4.json", "response4.json"),
                Arguments.of("request5.json", "response5.json"),
                Arguments.of("request6.json", "response6.json"),
                Arguments.of("request7.json", "response7.json"),
                Arguments.of("request8.json", "response8.json"),
                Arguments.of("request9.json", "response9.json")
        );
    }

}
