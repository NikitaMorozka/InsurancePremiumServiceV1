package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeCoefficientCalculatingTest {

    @Mock private AgeCoefficientRepository ageCoefficientRepository;
    @Mock private TravelCalculatePremiumRequest request;
    @InjectMocks private AgeCoefficientCalculating ageCoefficientCalculating;

    @Test
    void shouldReturnCorrectAgeCoefficient() {
        when(request.getDateOfBirth()).thenReturn(LocalDate.of(2003, 11,25 ));

        AgeCoefficient ageCoefficient = new AgeCoefficient(
                1L,
                20L,
                30L,
                new BigDecimal("1.20")
        );

        ReflectionTestUtils.setField(ageCoefficientCalculating, "medicalRiskAgeCoefficientEnabled", true);

        when(ageCoefficientRepository.findByAgeCoefficient(21)).thenReturn(Optional.of(ageCoefficient));

        BigDecimal result = ageCoefficientCalculating.findAgeCoefficient(request);

        assertEquals(new BigDecimal("1.20"), result);
    }
}

