package org.javaguru.travel.insurance.core.underwriting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.javaguru.travel.insurance.dto.Risks;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListRisks {
    private List<Risks> risks;
    private BigDecimal premium;
}
