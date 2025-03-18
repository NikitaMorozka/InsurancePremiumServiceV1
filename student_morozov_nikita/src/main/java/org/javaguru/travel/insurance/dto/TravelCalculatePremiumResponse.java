package org.javaguru.travel.insurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.travel.insurance.core.util.BigDecimalSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelCalculatePremiumResponse extends CoreResponse {
    private String personFirstName;
    private String personLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate dateOfBirth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") private LocalDate agreementDateTo;
    private String country;
    private String medicalRiskLimitLevel;
    @JsonSerialize(using = BigDecimalSerializer.class) private BigDecimal agreementPremium;
    private List<Risks> risks;

    public TravelCalculatePremiumResponse(List<ValidationError> errors) {
        super(errors);
    }

}
