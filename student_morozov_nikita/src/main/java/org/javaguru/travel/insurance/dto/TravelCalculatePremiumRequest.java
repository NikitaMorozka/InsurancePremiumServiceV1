package org.javaguru.travel.insurance.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelCalculatePremiumRequest {
    private String personFirstName;
    private String personLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateTo;
    private String country;
    private String medicalRiskLimitLevel;
    @JsonAlias("selected_risks") private List<String> selectedRisks;

}
