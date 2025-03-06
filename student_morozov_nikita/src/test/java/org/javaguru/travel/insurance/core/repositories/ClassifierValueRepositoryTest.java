package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierValueRepositoryTest {

    @Autowired private ClassifierValueRepository classifierValueRepository;

    @Test
    public void injectedRepositoryAreNotNull() {
        assertNotNull(classifierValueRepository);
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_MEDICAL() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_MEDICAL");
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_CANCELLATION() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_CANCELLATION");
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_LOSS_BAGGAGE() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_LOSS_BAGGAGE");
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_THIRD_PARTY_LIABILITY() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_THIRD_PARTY_LIABILITY");
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_EVACUATION() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_EVACUATION");
    }
    @Test
    public void shouldFind_RiskType_TRAVEL_SPORT_ACTIVITIES() {
        shouldFind_RiskType("RISK_TYPE", "TRAVEL_SPORT_ACTIVITIES");
    }
    @Test
    public void shouldNotFind_RiskType_FAKE() {
        Optional<ClassifierValue> valueOpt = classifierValueRepository.findByClassifierTitleAndIc(
                "RISK_TYPE", "FAKE");
        assertTrue(valueOpt.isEmpty());
    }
    public void shouldFind_RiskType(String classifierTitle, String ic) {
        Optional<ClassifierValue> valueOpt = classifierValueRepository.findByClassifierTitleAndIc(classifierTitle,ic);
        assertTrue(valueOpt.isPresent());
        assertEquals(valueOpt.get().getIc(), ic);
        assertEquals(valueOpt.get().getClassifier().getTitle(), classifierTitle);

    }
}