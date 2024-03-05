package com.nttdata.movement.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsInfo {
    @JsonProperty(value = "savingsGoal")
    private String savingsGoal;

    @JsonProperty(value = "interestRate")
    private BigDecimal interestRate;

    @JsonProperty(value = "monthlyMovements")
    private Long monthlyMovements;

}
